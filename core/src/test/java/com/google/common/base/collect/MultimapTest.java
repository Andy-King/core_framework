package com.google.common.base.collect;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * <p>&nbsp;　 &nbsp;Multimap也支持一系列强大的视图功能： 
 * <br>　　1.asMap把自身Multimap&lt;K, V&gt;映射成Map&lt;K, Collection&lt;V&gt;&gt;视图。这个Map视图支持remove和修改操作，但是不支持put和putAll。严格地来讲，当你希望传入参数是不存在的key，而且你希望返回的是null而不是一个空的可修改的集合的时候就可以调用asMap().get(key)。（你可以强制转型asMap().get(key)的结果类型－对SetMultimap的结果转成Set，对ListMultimap的结果转成List型－但是直接把ListMultimap转成Map&lt;K, List&lt;V&gt;&gt;是不行的。）<br>　　2.entries视图是把Multimap里所有的键值对以Collection&lt;Map.Entry&lt;K, V&gt;&gt;的形式展现。<br>　　3.keySet视图是把Multimap的键集合作为视图<br>　　4.keys视图返回的是个Multiset，这个Multiset是以不重复的键对应的个数作为视图。这个Multiset可以通过支持移除操作而不是添加操作来修改Multimap。<br>　　5.values()视图能把Multimap里的所有值“平展”成一个Collection&lt;V&gt;。这个操作和Iterables.concat(multimap.asMap().values())很相似，只是它返回的是一个完整的Collection。</p>
 *
 * <p>　　<strong>Multimap的实现</strong></p>
 * <p><strong>　</strong>Multimap提供了丰富的实现，所以你可以用它来替代程序里的Map&lt;K, Collection&lt;V&gt;&gt;，具体的实现如下：<br>　　Implementation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Keys 的行为类似&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 　　　Values的行为类似<br>　　ArrayListMultimap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HashMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 　　ArrayList<br>　　HashMultimap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HashMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　 HashSet<br>　　LinkedListMultimap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; LinkedHashMap*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; LinkedList*<br>　　LinkedHashMultimap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; LinkedHashMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; LinkedHashSet<br>　　TreeMultimap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; TreeMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; TreeSet<br>　　ImmutableListMultimap&nbsp; ImmutableMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; ImmutableList<br>　　ImmutableSetMultimap&nbsp; ImmutableMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ImmutableSet</p>
 */
public class MultimapTest {
		@Test
	    public void teststuScoreMultimap(){
	        Multimap<String,String> scoreMultimap = ArrayListMultimap.create(); 
	        for(int i=10;i<20;i++){
	           
	            scoreMultimap.put("peida",String.valueOf(i));
	        }
	        System.out.println("scoreMultimap:"+scoreMultimap.size());
	        System.out.println("scoreMultimap:"+scoreMultimap.keys());
	    }
		
		@Test
	    public void testMultimap(){
	        Multimap<String,String> scoreMultimap = ArrayListMultimap.create(); 
	        for(int i=10;i<20;i++){
	            scoreMultimap.put("peida",String.valueOf(i));
	        }
	        System.out.println("scoreMultimap size:"+scoreMultimap.size());
	        System.out.println("scoreMultimap keys:"+scoreMultimap.keys());
	        
	        Collection<String> studentScore = scoreMultimap.get("peida");
	        studentScore.clear();
	        String studentScoreNew="1034";
	        studentScore.add(studentScoreNew);
	        
	        System.out.println("scoreMultimap:"+scoreMultimap.size());
	        System.out.println("scoreMultimap:"+scoreMultimap.keys());
	    }
}
