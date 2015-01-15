package com.google.common.collect;


import org.junit.Test;

/**
 * <p>　　<strong>BiMap的实现类</strong></p>
 * <p><strong>Key-Value Map Impl&nbsp;&nbsp; &nbsp; Value-Key Map Impl&nbsp;&nbsp; &nbsp; Corresponding BiMap</strong><br>　　HashMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 　　&nbsp; HashMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HashBiMap<br>　　ImmutableMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 　　ImmutableMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; ImmutableBiMap<br>　　EnumMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　&nbsp; EnumMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; EnumBiMap<br>　　EnumMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 　　 HashMap&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; EnumHashBiMap</p>
 *
 */
public class BiMapTest {

	@Test
    public void BimapTest(){
        BiMap<Integer,String> logfileMap = HashBiMap.create(); 
        logfileMap.put(1,"a.log");
        logfileMap.put(2,"b.log");
        logfileMap.put(3,"c.log"); 
        System.out.println("logfileMap:"+logfileMap); 
        BiMap<String,Integer> filelogMap = logfileMap.inverse();
        System.out.println("filelogMap:"+filelogMap);
        
        logfileMap.put(4,"d.log"); 

        System.out.println("logfileMap:"+logfileMap); 
        System.out.println("filelogMap:"+filelogMap); 
    }

}
