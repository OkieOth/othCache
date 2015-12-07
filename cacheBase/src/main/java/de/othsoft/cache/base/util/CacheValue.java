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
package de.othsoft.cache.base.util;

import de.othsoft.cache.base.ICache;

/**
 *
 * @author eiko
 */
public class CacheValue<T> {    
    T value;
    String key;
    int expireSeconds;

    private final Class<T> type;
    
    public CacheValue(Class<T> t) {
        value = null;
        key = null;
        expireSeconds = ICache.NO_EXPIRES;
        type = t;
    }

    public CacheValue(Class<T> t,String key,T value, int expireSeconds) {
        this.value = value;
        this.key = key;
        this.expireSeconds = expireSeconds;
        this.type = t;
    }    
    
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }        

    public Class<T> getType() {
        return type;
    }
}
