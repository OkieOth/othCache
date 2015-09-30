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

/**
 *
 * @author eiko
 */
public interface ICache {
    String createUniqueUserId();
    
    void putStrValue(String appKey,String userKey,String value,boolean expires) throws CacheException;
    void putBoolValue(String appKey,String userKey,boolean value,boolean expires) throws CacheException;
    void putIntValue(String appKey,String userKey,int value,boolean expires) throws CacheException;
    void putLongValue(String appKey,String userKey,long value,boolean expires) throws CacheException;    
    void putStrArrayValue(String appKey,String userKey,String[] value,boolean expires) throws CacheException;
    void putBoolArrayValue(String appKey,String userKey,boolean[] value,boolean expires) throws CacheException;
    void putIntArrayValue(String appKey,String userKey,int[] value,boolean expires) throws CacheException;
    void putLongArrayValue(String appKey,String userKey,long[] value,boolean expires) throws CacheException;  

    String getStrValue(String appKey,String userKey) throws CacheException;
    Boolean getBoolValue(String appKey,String userKey) throws CacheException;
    Integer getIntValue(String appKey,String userKey) throws CacheException;
    Long getLongValue(String appKey,String userKey) throws CacheException; 
    String[] getStrArrayValue(String appKey,String userKey) throws CacheException;
    Boolean[] getBoolArrayValue(String appKey,String userKey) throws CacheException;
    Integer[] getIntArrayValue(String appKey,String userKey) throws CacheException;
    Long[] getLongArrayValue(String appKey,String userKey) throws CacheException;  
}
