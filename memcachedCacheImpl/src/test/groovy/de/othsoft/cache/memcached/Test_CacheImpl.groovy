/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.othsoft.cache.memcached

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.*
import de.othsoft.helper.base.Identifier
import de.othsoft.cache.base.util.CacheValue

/**
 *
 * @author eiko
 */
class Test_CacheImpl {
    boolean doLocalTest=true
    static private String serverAddress
    static private int serverPort
    static private CacheImpl cacheImpl
    final static private String APP_BASE = 'Test_CacheImpl'
    
    public Test_CacheImpl() {
    }

    @BeforeClass
    public static void setUpClass() {
        serverAddress = '127.0.0.1'
        serverPort = 11211
        Identifier.init(Test_CacheImpl.class)
        cacheImpl = new CacheImpl();
        cacheImpl.setServer(serverAddress,serverPort);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void simpleSetGetString() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetString_${new Date().time}";
        String value = "aäÄ ß?_${new Date().time}";
        try {
            assertNull('value already exists',cacheImpl.getStrValue(appKey,userKey1,entryKey));
            cacheImpl.setStrValue(appKey,userKey1,entryKey,value);
            String readedValue = cacheImpl.getStrValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetString: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getStrValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getStrValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetBoolFalse() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetBoolFalse_${new Date().time}";
        boolean value = false;
        try {
            assertNull('value already exists',cacheImpl.getBoolValue(appKey,userKey1,entryKey));
            cacheImpl.setBoolValue(appKey,userKey1,entryKey,value);
            Boolean readedValue = cacheImpl.getBoolValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetBoolFalse: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getBoolValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getIntValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetBoolTrue() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetBoolTrue_${new Date().time}";
        boolean value = true;
        try {
            assertNull('value already exists',cacheImpl.getBoolValue(appKey,userKey1,entryKey));
            cacheImpl.setBoolValue(appKey,userKey1,entryKey,value);
            Boolean readedValue = cacheImpl.getBoolValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetBoolTrue: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getBoolValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getIntValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetInt() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetInt_${new Date().time}";
        Integer value = 42;
        try {
            assertNull('value already exists',cacheImpl.getIntValue(appKey,userKey1,entryKey));
            cacheImpl.setIntValue(appKey,userKey1,entryKey,value);
            Integer readedValue = cacheImpl.getIntValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetInt: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getIntValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getIntValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetInt_Max() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetInt_${new Date().time}";
        Integer value = Integer.MAX_VALUE;
        try {
            assertNull('value already exists',cacheImpl.getIntValue(appKey,userKey1,entryKey));
            cacheImpl.setIntValue(appKey,userKey1,entryKey,value);
            Integer readedValue = cacheImpl.getIntValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetInt: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getIntValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getIntValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetInt_Min() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetInt_${new Date().time}";
        Integer value = Integer.MIN_VALUE;
        try {
            assertNull('value already exists',cacheImpl.getIntValue(appKey,userKey1,entryKey));
            cacheImpl.setIntValue(appKey,userKey1,entryKey,value);
            Integer readedValue = cacheImpl.getIntValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetInt: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getIntValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getIntValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetLong() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetInt_${new Date().time}";
        Long value = 100000000001;
        try {
            assertNull('value already exists',cacheImpl.getLongValue(appKey,userKey1,entryKey));
            cacheImpl.setLongValue(appKey,userKey1,entryKey,value);
            Long readedValue = cacheImpl.getLongValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetInt: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getLongValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getLongValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetLong_Max() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetInt_${new Date().time}";
        Long value = Long.MAX_VALUE;
        try {
            assertNull('value already exists',cacheImpl.getLongValue(appKey,userKey1,entryKey));
            cacheImpl.setLongValue(appKey,userKey1,entryKey,value);
            Long readedValue = cacheImpl.getLongValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetInt: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getLongValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getLongValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void simpleSetGetLong_Min() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        String entryKey = "simpleSetGetInt_${new Date().time}";
        Long value = Long.MIN_VALUE;
        try {
            assertNull('value already exists',cacheImpl.getLongValue(appKey,userKey1,entryKey));
            cacheImpl.setLongValue(appKey,userKey1,entryKey,value);
            Long readedValue = cacheImpl.getLongValue(appKey,userKey1,entryKey);
            assertEquals('simpleSetGetInt: wrong readedValue',value,readedValue);        
            assertNull('value for userKey2',cacheImpl.getLongValue(appKey,userKey2,entryKey));
        }
        finally {
            cacheImpl.removeValue(appKey,userKey1,entryKey); 
            assertNull('value not removed',cacheImpl.getLongValue(appKey,userKey1,entryKey));
        }
    }

    @Test
    public void setGetValues() {
        String appKey = cacheImpl.createUniqueAppKey(APP_BASE);        
        String userKey1 = cacheImpl.createUniqueUserKey("u1");
        String userKey2 = cacheImpl.createUniqueUserKey("u2");
        long time = new Date().time;
        String entryKeyInt = "setGetValues_int_${time}";
        String entryKeyLong = "setGetValues_long_${time}";
        String entryKeyBool = "setGetValues_bool_${time}";
        String entryKeyBool2 = "setGetValues_bool2_${time}";
        String entryKeyStr = "setGetValues_str_${time}";
        
        int valueInt = 23;
        long valueLong = 23000000;
        boolean valueBool = true;
        boolean valueBool2 = false;
        String valueStr = 'hello world';
        int expireSeconds = 60;
        def valuesToSave = [
            new CacheValue(Integer.class,entryKeyInt,valueInt,expireSeconds),
            new CacheValue(Long.class,entryKeyLong,valueLong,expireSeconds),
            new CacheValue(Boolean.class,entryKeyBool,valueBool,expireSeconds),
            new CacheValue(Boolean.class,entryKeyBool2,valueBool2,expireSeconds),
            new CacheValue(String.class,entryKeyStr,valueStr,expireSeconds),
        ];

        def valuesToRead = [
            new CacheValue(Integer.class,entryKeyInt,null,expireSeconds),
            new CacheValue(Long.class,entryKeyLong,null,expireSeconds),
            new CacheValue(Boolean.class,entryKeyBool,null,expireSeconds),
            new CacheValue(Boolean.class,entryKeyBool2,null,expireSeconds),
            new CacheValue(String.class,entryKeyStr,null,expireSeconds),
        ];

        try {
            cacheImpl.getValues(appKey,userKey1,valuesToRead);
            assertNull('int value already exists',valuesToRead[0].getValue());
            assertNull('long value already exists',valuesToRead[1].getValue());
            assertNull('bool value already exists',valuesToRead[2].getValue());
            assertNull('bool2 value already exists',valuesToRead[3].getValue());
            assertNull('string value already exists',valuesToRead[4].getValue());
            cacheImpl.setValues(appKey,userKey1,valuesToSave);
            cacheImpl.getValues(appKey,userKey1,valuesToRead);

            assertNotNull('int value not exists',valuesToRead[0].getValue());
            assertNotNull('long value not exists',valuesToRead[1].getValue());
            assertNotNull('bool value not exists',valuesToRead[2].getValue());
            assertNotNull('bool2 value not exists',valuesToRead[3].getValue());
            assertNotNull('string value not exists',valuesToRead[4].getValue());

            assertEquals('int: wrong readedValue',valuesToSave[0].getValue(),valuesToRead[0].getValue());        
            assertEquals('long: wrong readedValue',valuesToSave[1].getValue(),valuesToRead[1].getValue());        
            assertEquals('bool: wrong readedValue',valuesToSave[2].getValue(),valuesToRead[2].getValue());        
            assertEquals('bool2: wrong readedValue',valuesToSave[3].getValue(),valuesToRead[3].getValue());        
            assertEquals('string: wrong readedValue',valuesToSave[4].getValue(),valuesToRead[4].getValue());        
            
            def valuesToRead2 = [
                new CacheValue(Integer.class,entryKeyInt,null,expireSeconds),
                new CacheValue(Long.class,entryKeyLong,null,expireSeconds),
                new CacheValue(Boolean.class,entryKeyBool,null,expireSeconds),
                new CacheValue(Boolean.class,entryKeyBool2,null,expireSeconds),
                new CacheValue(String.class,entryKeyStr,null,expireSeconds),
            ];
            cacheImpl.getValues(appKey,userKey2,valuesToRead2);
            assertNull('int value for userKey2',valuesToRead2[0].getValue());
            assertNull('long value for userKey2',valuesToRead2[1].getValue());
            assertNull('bool value for userKey2',valuesToRead2[2].getValue());
            assertNull('bool2 value for userKey2',valuesToRead2[3].getValue());
            assertNull('string value for userKey2',valuesToRead2[4].getValue());
        }
        finally {      
            def valuesToRead3 = [
                new CacheValue(Integer.class,entryKeyInt,null,expireSeconds),
                new CacheValue(Long.class,entryKeyLong,null,expireSeconds),
                new CacheValue(Boolean.class,entryKeyBool,null,expireSeconds),
                new CacheValue(Boolean.class,entryKeyBool2,null,expireSeconds),
                new CacheValue(String.class,entryKeyStr,null,expireSeconds),
            ];            
            cacheImpl.removeValues(appKey,userKey1,[entryKeyInt,entryKeyLong,entryKeyBool,entryKeyBool2,entryKeyStr]); 
            assertNull('int value not removed',valuesToRead3[0].getValue());
            assertNull('long value not removed',valuesToRead3[1].getValue());
            assertNull('bool value not removed',valuesToRead3[2].getValue());
            assertNull('bool2 value not removed',valuesToRead3[3].getValue());
            assertNull('string value not removed',valuesToRead3[4].getValue());
        }
    }
}
