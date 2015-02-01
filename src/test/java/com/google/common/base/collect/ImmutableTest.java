package com.google.common.base.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;

/**
 * <pre>
　　　为什么要用immutable对象？immutable对象有以下的优点：
　　　1.对不可靠的客户代码库来说，它使用安全，可以在未受信任的类库中安全的使用这些对象
　　　2.线程安全的：immutable对象在多线程下安全，没有竞态条件
　　　3.不需要支持可变性, 可以尽量节省空间和时间的开销. 所有的不可变集合实现都比可变集合更加有效的利用内存 (analysis)
　　　4.可以被使用为一个常量，并且期望在未来也是保持不变的

　　　immutable对象可以很自然地用作常量，因为它们天生就是不可变的对于immutable对象的运用来说，它是一个很好的防御编程（defensive programming）的技术实践。
 * </pre>
 * 
 * <p>　　<strong>Multiset不是Map</strong></p>
 * <p>　　需要注意的是Multiset不是一个Map&lt;E,Integer&gt;,尽管Multiset提供一部分类似的功能实现。其它值得关注的差别有:<br>　　Multiset中的元素的重复个数只会是正数，且最大不会超过Integer.MAX_VALUE。设定计数为0的元素将不会出现multiset中，也不会出现elementSet()和entrySet()的返回结果中。<br>　　multiset.size() 方法返回的是所有的元素的总和，相当于是将所有重复的个数相加。如果需要知道每个元素的个数可以使用elementSet().size()得到.(因而调用add(E)方法会是multiset.size()增加1).<br>　　multiset.iterator() 会循环迭代每一个出现的元素，迭代的次数与multiset.size()相同。 iterates over each occurrence of each element, so the length of the iteration is equal to multiset.size().<br>　　Multiset 支持添加、移除多个元素以及重新设定元素的个数。执行setCount(element,0)相当于移除multiset中所有的相同元素。<br>　　调用multiset.count(elem)方法时，如果该元素不在该集中，那么返回的结果只会是0。</p>
 * <p>　　<strong>Multiset的实现</strong>　</p>
 * <p>　　Guava提供了Multiset的多种实现，这些实现基本对应了JDK中Map的实现： <br>　　<strong>Map&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Corresponding Multiset&nbsp;&nbsp; Supports null elements</strong><br>　　HashMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HashMultiset&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp; Yes<br>　　TreeMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; TreeMultiset&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Yes (if the comparator does)<br>　　LinkedHashMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; LinkedHashMultiset&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Yes<br>　　ConcurrentHashMap&nbsp; ConcurrentHashMultiset&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; No<br>　　ImmutableMap&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; ImmutableMultiset&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; No</p>
 * 
 * <p>　　<strong>Guava集合和不可变对应关系</strong></p>
 * <table class="wikitable" align="center" border="1px">
 * <tbody>
 * <tr>
 * <td style="text-align: left;"><strong>可变集合类型</strong></td>
 * <td><strong>可变集合源：JDK or Guava?</strong></td>
 * <td><strong>Guava不可变集合</strong></td>
 * </tr>
 * <tr>
 * <td><tt>Collection</tt></td>
 * <td>JDK</td>
 * <td><tt>ImmutableCollection</tt></td>
 * </tr>
 * <tr>
 * <td><tt>List</tt></td>
 * <td>JDK</td>
 * <td><tt>ImmutableList</tt></td>
 * </tr>
 * <tr>
 * <td><tt>Set</tt></td>
 * <td>JDK</td>
 * <td><tt>ImmutableSet</tt></td>
 * </tr>
 * <tr>
 * <td><tt>SortedSet</tt>/<tt>NavigableSet</tt></td>
 * <td>JDK</td>
 * <td><tt>ImmutableSortedSet</tt></td>
 * </tr>
 * <tr>
 * <td><tt>Map</tt></td>
 * <td>JDK</td>
 * <td><tt>ImmutableMap</tt></td>
 * </tr>
 * <tr>
 * <td><tt>SortedMap</tt></td>
 * <td>JDK</td>
 * <td><tt>ImmutableSortedMap</tt></td>
 * </tr>
 * <tr>
 * <td>Multiset</td>
 * <td>Guava</td>
 * <td><tt>ImmutableMultiset</tt></td>
 * </tr>
 * <tr>
 * <td><tt>SortedMultiset</tt></td>
 * <td>Guava</td>
 * <td><tt>ImmutableSortedMultiset</tt></td>
 * </tr>
 * <tr>
 * <td>Multimap</td>
 * <td>Guava</td>
 * <td><tt>ImmutableMultimap</tt></td>
 * </tr>
 * <tr>
 * <td><tt>ListMultimap</tt></td>
 * <td>Guava</td>
 * <td><tt>ImmutableListMultimap</tt></td>
 * </tr>
 * <tr>
 * <td><tt>SetMultimap</tt></td>
 * <td>Guava</td>
 * <td><tt>ImmutableSetMultimap</tt></td>
 * </tr>
 * <tr>
 * <td>BiMap</td>
 * <td>Guava</td>
 * <td><tt>ImmutableBiMap</tt></td>
 * </tr>
 * <tr>
 * <td>ClassToInstanceMap</td>
 * <td>Guava</td>
 * <td><tt>ImmutableClassToInstanceMap</tt></td>
 * </tr>
 * <tr>
 * <td>Table</td>
 * <td>Guava</td>
 * <td><tt>ImmutableTable</tt></td>
 * </tr>
 * </tbody>
 * </table>
 *
 */
public class ImmutableTest {
    @Test
    public void testJDKImmutable(){                                                                                                                                                                                                                                    
        List<String> list=new ArrayList<String>();                                                                               
        list.add("a");                                                                                                           
        list.add("b");                                                                                                           
        list.add("c");
        
        System.out.println(list);
        
        List<String> unmodifiableList=Collections.unmodifiableList(list); 
        
        System.out.println(unmodifiableList);
        
        List<String> unmodifiableList1=Collections.unmodifiableList(Arrays.asList("a","b","c")); 
        System.out.println(unmodifiableList1);
        
        String temp=unmodifiableList.get(1);
        System.out.println("unmodifiableList [0]："+temp);
                
        list.add("baby");
        System.out.println("list add a item after list:"+list);
        System.out.println("list add a item after unmodifiableList:"+unmodifiableList);
        
        unmodifiableList1.add("bb");
        System.out.println("unmodifiableList add a item after list:"+unmodifiableList1);
        
        unmodifiableList.add("cc");
        System.out.println("unmodifiableList add a item after list:"+unmodifiableList);        
    }
    
    @Test
    public void testCotyOf(){
        ImmutableSet<String> imSet=ImmutableSet.of("peida","jerry","harry","lisa");
        System.out.println("imSet："+imSet);
        ImmutableList<String> imlist=ImmutableList.copyOf(imSet);
        System.out.println("imlist："+imlist);
        ImmutableSortedSet<String> imSortSet=ImmutableSortedSet.copyOf(imSet);
        System.out.println("imSortSet："+imSortSet);
        
        List<String> list=new ArrayList<String>();
        for(int i=0;i<20;i++){
            list.add(i+"x");
        }
        System.out.println("list："+list);
        ImmutableList<String> imInfolist=ImmutableList.copyOf(list.subList(2, 18));
        System.out.println("imInfolist："+imInfolist);
        int imInfolistSize=imInfolist.size();
        System.out.println("imInfolistSize："+imInfolistSize);
        ImmutableSet<String> imInfoSet=ImmutableSet.copyOf(imInfolist.subList(2, imInfolistSize-3));
        System.out.println("imInfoSet："+imInfoSet);
    }
    
    @Test
    public void testAsList(){
        ImmutableList<String> imList=ImmutableList.of("peida","jerry","harry","lisa","jerry");
        System.out.println("imList："+imList);
        ImmutableSortedSet<String> imSortList=ImmutableSortedSet.copyOf(imList);
        System.out.println("imSortList："+imSortList);
        System.out.println("imSortList as list："+imSortList.asList());
    }
    
    @Test
    public void testMultsetWordCount(){
        String strWorld="wer|dfd|dd|dfd|dda|de|dr";
        String[] words=strWorld.split("\\|");
        List<String> wordList=new ArrayList<String>();
        for (String word : words) {
            wordList.add(word);
        }
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(wordList);
        
        
        //System.out.println("wordsMultiset："+wordsMultiset);
        
        for(String key:wordsMultiset.elementSet()){
            System.out.println(key+" count："+wordsMultiset.count(key));
        }
        
        if(!wordsMultiset.contains("peida")){
            wordsMultiset.add("peida", 2);
        }
        System.out.println("============================================");
        for(String key:wordsMultiset.elementSet()){
            System.out.println(key+" count："+wordsMultiset.count(key));
        }
        
        
        if(wordsMultiset.contains("peida")){
            wordsMultiset.setCount("peida", 23);
        }
        
        System.out.println("============================================");
        for(String key:wordsMultiset.elementSet()){
            System.out.println(key+" count："+wordsMultiset.count(key));
        }
        
        if(wordsMultiset.contains("peida")){
            wordsMultiset.setCount("peida", 23,45);
        }
        
        System.out.println("============================================");
        for(String key:wordsMultiset.elementSet()){
            System.out.println(key+" count："+wordsMultiset.count(key));
        }
        
        if(wordsMultiset.contains("peida")){
            wordsMultiset.setCount("peida", 44,67);
        }
        
        System.out.println("============================================");
        for(String key:wordsMultiset.elementSet()){
            System.out.println(key+" count："+wordsMultiset.count(key));
        }
        
        System.out.println("============================================");
        for(String key:wordsMultiset){
        	System.out.println(key);
        }
    }
}