/*
Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
See the NOTICE file distributed with this work for additional information regarding copyright ownership.  
The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
specific language governing permissions and limitations under the License.
*/
package de.othsoft.cache.memcached;

import de.othsoft.cache.base.ICache;
import de.othsoft.cache.base.error.CacheException;
import de.othsoft.cache.base.util.CacheValue;
import de.othsoft.helper.base.Identifier;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.IntegerTranscoder;
import net.rubyeye.xmemcached.transcoders.LongTranscoder;
import net.rubyeye.xmemcached.transcoders.StringTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eiko
 */
public class CacheImpl implements ICache {
    private final static int MAX_EXPIRES=7*24*60*60; // one week - no better idea
    MemcachedClient client = null;
    String serverAddr = null;
    int serverPort = 0;
    long timeout=5000;
    
    private static long appCount;
    private static long userCount;
    
    private final static long KEY_BASE = new Date().getTime();
    
    private static final StringTranscoder STRING_TRANSCODER = new StringTranscoder("UTF-8");
    private static final IntegerTranscoder INT_TRANSCODER = new IntegerTranscoder();
    private static final LongTranscoder LONG_TRANSCODER = new LongTranscoder();
    
    /**
     * 
     * @param address address of memcached server, something like 127.0.0.1:12000 
     */
    public void setServer(String address,int port) {
        try {
            this.serverAddr=address;
            if (client!=null) {
                client = null;
            }
            client=new XMemcachedClient(address,port);
        }
        catch(IOException e) {
            logger.error("<<{}>> error while create memcached client for address {}: [{}] {}",
                    Identifier.getInst().getName(),address,e.getClass().getName(),e.getMessage());
            client = null;
            this.serverAddr = null;
        }
    }
    
    public void closeServerCon() {
        if (client instanceof XMemcachedClient) {
            logger.info("<<{}>> remove server with addr {}",
                    Identifier.getInst().getName(),serverAddr);
            try {
                ((XMemcachedClient)client).shutdown();
            }
            catch(IOException io) {
                logger.error("<<{}>> error while close memcached client for address {}: [{}] {}",
                        Identifier.getInst().getName(),serverAddr,io.getClass().getName(),io.getMessage());                
            }
        }
        client = null;
    }
                                        
    private void checkInitAndIfWrongThrowException() throws CacheException {
        if (client==null) {
            throw new CacheException("memcached client not initialized");
        }
    }
    
    private String getMemcachedKey(String appKey,String userKey,String entryKey) {
        return appKey+"_"+userKey+"_"+entryKey;
    }
    
    @Override
    public synchronized String createUniqueUserKey(String base) throws CacheException{
        userCount++;
        return String.format("%d-%s-%d", KEY_BASE,base,userCount);
    }

    @Override
    public synchronized String createUniqueAppKey(String base) throws CacheException{
        appCount++;
        return String.format("%d-%s-%d", KEY_BASE,base,appCount);
    }

    
    @Override
    public void setStrValue(String appKey,String userKey,String entryKey, String value,int expireSeconds) throws CacheException {
        if (value==null) throw new CacheException("setStrValue - null values are not allowed");
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey, entryKey);
        try {
            client.set(memcachedKey,expireSeconds,value);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setStrValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }        
    }
    
    @Override
    public void setStrValue(String appKey,String userKey,String entryKey,String value) throws CacheException {
        if (value==null) throw new CacheException("setStrValue - null values are not allowed");
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {
            client.set(memcachedKey,MAX_EXPIRES,value);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setStrValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        } 
    }
    

    @Override
    public void setBoolValue(String appKey,String userKey,String entryKey,Boolean value,int expireSeconds) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {
            if (value==null)
                client.delete(memcachedKey);                
            else {
                int iValue = value ? 1 : 0;
                client.set(memcachedKey,expireSeconds,iValue);
            }
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setBoolValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public void setBoolValue(String appKey,String userKey,String entryKey,Boolean value) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {
            if (value==null)
                client.delete(memcachedKey);                
            else {
                int iValue = value ? 1 : 0;
                client.set(memcachedKey,MAX_EXPIRES,iValue);
            }
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setBoolValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }                 
    }
    

    @Override
    public void setIntValue(String appKey,String userKey,String entryKey,Integer value,int expireSeconds) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {
            if (value==null)
                client.delete(memcachedKey);
            else
                client.set(memcachedKey,expireSeconds,value);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setIntValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public void setIntValue(String appKey,String userKey,String entryKey,Integer value) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {
            if (value==null)
                client.delete(memcachedKey);
            else
                client.set(memcachedKey,MAX_EXPIRES,value);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setIntValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public void setLongValue(String appKey,String userKey,String entryKey,Long value,int expireSeconds) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {
            if (value==null)
                client.delete(memcachedKey);
            else
                client.set(memcachedKey,expireSeconds,value);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setLongValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }    

    @Override
    public void setLongValue(String appKey,String userKey,String entryKey,Long value) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {            
            if (value==null)
                client.delete(memcachedKey);
            else
                client.set(memcachedKey,MAX_EXPIRES,value);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setLongValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public void setValues(String appKey,String userKey,List<CacheValue> cacheValueArray) throws CacheException {
        checkInitAndIfWrongThrowException();
        try {            
            for (CacheValue cacheValue:cacheValueArray) {
                String memcachedKey=getMemcachedKey(appKey, userKey,cacheValue.getKey());
                if (cacheValue.getType()==String.class) {
                    String v = (String) cacheValue.getValue();
                    if (v==null)
                        client.delete(memcachedKey);
                    else
                        client.set(memcachedKey,cacheValue.getExpireSeconds(),v);
                }
                else if (cacheValue.getType()==Integer.class) {
                    Integer v = (Integer) cacheValue.getValue();
                    if (v==null)
                        client.delete(memcachedKey);
                    else
                        client.set(memcachedKey,cacheValue.getExpireSeconds(),v);
                }
                else if (cacheValue.getType()==Long.class) {
                    Long v = (Long) cacheValue.getValue();
                    if (v==null)
                        client.delete(memcachedKey);
                    else
                        client.set(memcachedKey,cacheValue.getExpireSeconds(),v);
                }
                else if (cacheValue.getType()==Boolean.class) {
                    Boolean b = (Boolean)cacheValue.getValue();
                    if (b==null)
                        client.delete(memcachedKey);
                    else if (b==true)
                        client.set(memcachedKey,cacheValue.getExpireSeconds(),1);
                    else
                        client.set(memcachedKey,cacheValue.getExpireSeconds(),0);
                }

            }
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setValues - app: %s, user: %s",appKey,userKey));
        }         
    }
    
    @Override
    public void touchValues(String appKey,String userKey,List<String> keyArray,int expireSeconds) throws CacheException {
        checkInitAndIfWrongThrowException();
        try {            
            for (String entryKey:keyArray) {
                String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
                client.touch(memcachedKey,expireSeconds);
            }
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while touchValues - app: %s, user: %s",appKey,userKey));
        }                 
    }
    

    @Override
    public void touchValue(String appKey,String userKey,String entryKey,int expireSeconds) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {            
            client.touch(memcachedKey,expireSeconds);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while touchValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public void removeValues(String appKey,String userKey,List<String> keyArray) throws CacheException {
        checkInitAndIfWrongThrowException();
        try {            
            for (String entryKey:keyArray) {
                String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
                client.delete(memcachedKey);
            }
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while removeValues - app: %s, user: %s",
                    appKey,userKey));
        }         
    }
    

    @Override
    public void removeValue(String appKey,String userKey,String entryKey) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {            
            client.delete(memcachedKey);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while removeValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    


    @Override    
    public String getStrValue(String appKey,String userKey,String entryKey) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {            
            return client.get(memcachedKey,timeout,STRING_TRANSCODER);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while getStrValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public Boolean getBoolValue(String appKey,String userKey,String entryKey) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {            
            Integer i = client.get(memcachedKey,timeout,INT_TRANSCODER);
            if (i==null) return null;
            return i==1;
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while getBoolValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public Integer getIntValue(String appKey,String userKey,String entryKey) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {            
            return client.get(memcachedKey,timeout,INT_TRANSCODER);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while getIntValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    

    @Override
    public Long getLongValue(String appKey,String userKey,String entryKey) throws CacheException {
        checkInitAndIfWrongThrowException();
        String memcachedKey=getMemcachedKey(appKey, userKey,entryKey);
        try {            
            return client.get(memcachedKey,timeout,LONG_TRANSCODER);
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while getLongValue - app: %s, user: %s, key: %s",
                    appKey,userKey,entryKey));
        }         
    }
    
    @Override
    public void getValues(String appKey,String userKey,List<CacheValue> cacheValueArray) throws CacheException {
        checkInitAndIfWrongThrowException();
        try {            
            for (CacheValue cacheValue:cacheValueArray) {
                String memcachedKey=getMemcachedKey(appKey, userKey,cacheValue.getKey());
                if (cacheValue.getType()==String.class) {
                    cacheValue.setValue(client.get(memcachedKey,timeout,STRING_TRANSCODER));                    
                }
                else if (cacheValue.getType()==Integer.class) {
                    cacheValue.setValue(client.get(memcachedKey,timeout,INT_TRANSCODER));                    
                }
                else if (cacheValue.getType()==Long.class) {
                    cacheValue.setValue(client.get(memcachedKey,timeout,LONG_TRANSCODER));                    
                }
                else if (cacheValue.getType()==Boolean.class) {
                    Integer i = client.get(memcachedKey,timeout,INT_TRANSCODER);
                    if (i==null)
                        cacheValue.setValue(null);
                    else if (i==1)
                        cacheValue.setValue(true);
                    else
                        cacheValue.setValue(false);
                }
            }
        }
        catch(TimeoutException | InterruptedException | MemcachedException e) {
            throw new CacheException(e,String.format("error while setValues - app: %s, user: %s",appKey,userKey));
        }         
    }
    
    private static Logger logger = LoggerFactory.getLogger(CacheImpl.class);
}
