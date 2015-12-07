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
package de.othsoft.cache.base;

import de.othsoft.cache.base.error.CacheException;
import de.othsoft.cache.base.util.CacheValue;
import java.util.List;

/**
 *
 * @author eiko
 */
public interface ICache {
    static final int NO_EXPIRES=-1;
    String createUniqueUserKey(String base) throws CacheException;
    String createUniqueAppKey(String base) throws CacheException;
    
    void setStrValue(String appKey,String userKey,String entryKey,String value,int expireSeconds) throws CacheException;
    void setStrValue(String appKey,String userKey,String entryKey,String value) throws CacheException;
    void setBoolValue(String appKey,String userKey,String entryKey,Boolean value,int expireSeconds) throws CacheException;
    void setBoolValue(String appKey,String userKey,String entryKey,Boolean value) throws CacheException;
    void setIntValue(String appKey,String userKey,String entryKey,Integer value,int expireSeconds) throws CacheException;
    void setIntValue(String appKey,String userKey,String entryKey,Integer value) throws CacheException;
    void setLongValue(String appKey,String userKey,String entryKey,Long value,int expireSeconds) throws CacheException;   
    void setLongValue(String appKey,String userKey,String entryKey,Long value) throws CacheException;   
    void setValues(String appKey,String userKey,List<CacheValue> cacheValueArray) throws CacheException;
    void touchValues(String appKey,String userKey,List<String> keyArray,int expireSeconds) throws CacheException;
    void touchValue(String appKey,String userKey,String entryKey,int expireSeconds) throws CacheException;
    void removeValues(String appKey,String userKey,List<String> keyArray) throws CacheException;
    void removeValue(String appKey,String userKey,String entryKey) throws CacheException;
    
    // TODO - float values
    
    
    String getStrValue(String appKey,String userKey,String entryKey) throws CacheException;
    Boolean getBoolValue(String appKey,String userKey,String entryKey) throws CacheException;
    Integer getIntValue(String appKey,String userKey,String entryKey) throws CacheException;
    Long getLongValue(String appKey,String userKey,String entryKey) throws CacheException; 
    void getValues(String appKey,String userKey,List<CacheValue> cacheValueArray) throws CacheException;    
}
