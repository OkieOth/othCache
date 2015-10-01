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
/*
 * heavy inspired from https://code.google.com/p/xmemcached/wiki/Getting_Started
*/
package de.othsoft.cache;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author eiko
 */
public class Test_Connect {
    @Test
    public void test_connect() throws IOException, TimeoutException, InterruptedException,MemcachedException {
        MemcachedClient client=new XMemcachedClient("localhost",12211);
        String key="testString";
        String testValue="döfadöfadf dfösdföasdöferaföa adföasdf d öfödföadföaöfdöaföaf";
        Assert.assertNull(client.get(key));

        //store a value for one hour(synchronously).
        client.set(key,3600,testValue);
        //Retrieve a value.(synchronously).
        String readedValue=(String)client.get(key);
        Assert.assertEquals(testValue,readedValue);
        //Retrieve a value.(synchronously),operation timeout two seconds.
        //someObject=client.get(key,2000);

        //Touch cache item ,update it's expire time to 10 seconds.
        Assert.assertTrue(client.touch(key,10));

        //delete value
        client.delete(key);

        Assert.assertNull(client.get(key));
    }
    
    @Test
    public void test_expire() throws IOException, TimeoutException, InterruptedException,MemcachedException {
        MemcachedClient client=new XMemcachedClient("127.0.0.1",12211);
        String key="testString";
        String testValue="döfadöfadf dfösdföasdöferaföa adföasdf d öfödföadföaöfdöaföaf";
        Assert.assertNull(client.get(key));

        try {        
        //store a value for five seconds.
        client.set(key,5,testValue);
        //Retrieve a value.(synchronously).
        String readedValue=(String)client.get(key);
        Assert.assertEquals(testValue,readedValue);
        
        Thread.sleep(1000);

        readedValue=(String)client.get(key);
        Assert.assertEquals(testValue,readedValue);

        Thread.sleep(1000);

        readedValue=(String)client.get(key);
        Assert.assertEquals(testValue,readedValue);

        Thread.sleep(1000);

        readedValue=(String)client.get(key);
        Assert.assertEquals(testValue,readedValue);
        
        Thread.sleep(1000);

        readedValue=(String)client.get(key);
        Assert.assertEquals(testValue,readedValue);

        Thread.sleep(2000);

        readedValue=(String)client.get(key);
        Assert.assertNull(readedValue);
        }
        finally {
            // delete value - tidy up
            client.delete(key);
        }
    }

}
