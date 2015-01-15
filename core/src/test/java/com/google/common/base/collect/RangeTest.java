package com.google.common.base.collect;

import org.junit.Test;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

/**
 * <pre>
　　在Guava中新增了一个新的类型Range，从名字就可以了解到，这个是和区间有关的数据结构。从Google官方文档可以得到定义：Range定义了连续跨度的范围边界，这个连续跨度是一个可以比较的类型(Comparable type)。比如1到100之间的整型数据。
　　在数学里面的范围是有边界和无边界之分的；同样，在Guava中也有这个说法。如果这个范围是有边界的，那么这个范围又可以分为包括开集（不包括端点）和闭集（包括端点）；如果是无解的可以用+∞表示。如果枚举的话，一共有九种范围表示：
 * <pre>
 * 
 * <table style="height: 249px; width: 523px;" border="1px" align="center"><caption><strong>Guava Range 概念，范围和方法</strong></caption>
 * <tbody>
 * <tr>
 * <td style="text-align: center;"><strong>概念</strong></td>
 * <td style="text-align: center;"><strong>表示范围</strong></td>
 * <td style="text-align: center;"><strong>guava对应功能方法</strong></td>
 * </tr>
 * <tr>
 * <td style="text-align: center;"><span style="font-size: 13px;">(a..b)</span></td>
 * <td style="text-align: center;"><span style="font-size: 13px;">{x | a &lt; x &lt; b}</span></td>
 * <td style="text-align: center;"><span style="font-size: 13px;">open(C, C)<br></span></td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">[a..b]</td>
 * <td style="text-align: center;">{x | a &lt;= x &lt;= b}&nbsp;</td>
 * <td style="text-align: center;">closed(C, C)</td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">[a..b)</td>
 * <td style="text-align: center;">{x | a &lt;= x &lt; b}</td>
 * <td style="text-align: center;">closedOpen(C, C)</td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">(a..b]</td>
 * <td style="text-align: center;">{x | a &lt; x &lt;= b}</td>
 * <td style="text-align: center;">openClosed(C, C)</td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">(a..+∞)</td>
 * <td style="text-align: center;">{x | x &gt; a}</td>
 * <td style="text-align: center;">greaterThan(C)</td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">[a..+∞)</td>
 * <td style="text-align: center;">{x | x &gt;= a}</td>
 * <td style="text-align: center;">atLeast(C)</td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">(-∞..b)</td>
 * <td style="text-align: center;">{x | x &lt; b}</td>
 * <td style="text-align: center;">lessThan(C)</td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">(-∞..b]</td>
 * <td style="text-align: center;">{x | x &lt;= b}</td>
 * <td style="text-align: center;">atMost(C)</td>
 * </tr>
 * <tr>
 * <td style="text-align: center;">(-∞..+∞)</td>
 * <td style="text-align: center;">all values</td>
 * <td style="text-align: center;">all()</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class RangeTest {
	@Test
    public void testRange(){
        System.out.println("open:"+Range.open(1, 10));
        System.out.println("closed:"+ Range.closed(1, 10));
        System.out.println("closedOpen:"+ Range.closedOpen(1, 10));
        System.out.println("openClosed:"+ Range.openClosed(1, 10));    
        System.out.println("greaterThan:"+ Range.greaterThan(10));
        System.out.println("atLeast:"+ Range.atLeast(10));
        System.out.println("lessThan:"+ Range.lessThan(10));
        System.out.println("atMost:"+ Range.atMost(10));
        System.out.println("all:"+ Range.all());
        System.out.println("closed:"+Range.closed(10, 10));
        System.out.println("closedOpen:"+Range.closedOpen(10, 10));
        //会抛出异常
        System.out.println("open:"+Range.open(10, 10));
    }
	
    @Test
    public void testDownRange(){
        System.out.println("downTo:"+Range.downTo(4, BoundType.OPEN));
        System.out.println("upTo:"+Range.upTo(4, BoundType.CLOSED));
        System.out.println("range:"+Range.range(1, BoundType.CLOSED, 4, BoundType.OPEN)); 
    }
    
    @Test
    public void testContains(){
        System.out.println(Range.closed(1, 3).contains(2)); 
        System.out.println(Range.closed(1, 3).contains(4)); 
        System.out.println(Range.lessThan(5).contains(5)); 
        System.out.println(Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3))); 
    }
    
    @Test
    public void testQuery(){
        System.out.println("hasLowerBound:"+Range.closedOpen(4, 4).hasLowerBound()); 
        System.out.println("hasUpperBound:"+Range.closedOpen(4, 4).hasUpperBound()); 
        System.out.println(Range.closedOpen(4, 4).isEmpty()); 
        System.out.println(Range.openClosed(4, 4).isEmpty()); 
        System.out.println(Range.closed(4, 4).isEmpty()); 
        // Range.open throws IllegalArgumentException
        //System.out.println(Range.open(4, 4).isEmpty()); 

        System.out.println(Range.closed(3, 10).lowerEndpoint());
        System.out.println(Range.open(3, 10).lowerEndpoint()); 
        System.out.println(Range.closed(3, 10).upperEndpoint()); 
        System.out.println(Range.open(3, 10).upperEndpoint()); 
        System.out.println(Range.closed(3, 10).lowerBoundType()); 
        System.out.println(Range.open(3, 10).upperBoundType()); 
    }
    
    @Test
    public void testEncloses(){
        Range<Integer> rangeBase=Range.open(1, 4);
        Range<Integer> rangeClose=Range.closed(2, 3);
        Range<Integer> rangeCloseOpen=Range.closedOpen(2, 4);
        Range<Integer> rangeCloseOther=Range.closedOpen(2, 5);
        System.out.println("rangeBase: "+rangeBase+" Enclose:"+rangeBase.encloses(rangeClose)+" rangeClose:"+rangeClose);
        System.out.println("rangeBase: "+rangeBase+" Enclose:"+rangeBase.encloses(rangeCloseOpen)+" rangeClose:"+rangeCloseOpen);
        System.out.println("rangeBase: "+rangeBase+" Enclose:"+rangeBase.encloses(rangeCloseOther)+" rangeClose:"+rangeCloseOther);
    }
    
    @Test
    public void testConnected(){
        System.out.println(Range.closed(3, 5).isConnected(Range.open(5, 10))); 
        System.out.println(Range.closed(0, 9).isConnected(Range.closed(3, 4)));
        System.out.println(Range.closed(0, 5).isConnected(Range.closed(3, 9))); 
        System.out.println(Range.open(3, 5).isConnected(Range.open(5, 10))); 
        System.out.println(Range.closed(1, 5).isConnected(Range.closed(6, 10)));
    }
    
    @Test
    public void testIntersection(){
        System.out.println(Range.closed(3, 5).intersection(Range.open(5, 10))); 
        System.out.println(Range.closed(0, 9).intersection(Range.closed(3, 4))); 
        System.out.println(Range.closed(0, 5).intersection(Range.closed(3, 9))); 
        System.out.println(Range.open(3, 5).intersection(Range.open(5, 10)));
        System.out.println(Range.closed(1, 5).intersection(Range.closed(6, 10)));
    }
    
    @Test
    public void testSpan(){
        System.out.println(Range.closed(3, 5).span(Range.open(5, 10))); 
        System.out.println(Range.closed(0, 9).span(Range.closed(3, 4))); 
        System.out.println(Range.closed(0, 5).span(Range.closed(3, 9))); 
        System.out.println(Range.open(3, 5).span(Range.open(5, 10))); 
        System.out.println(Range.closed(1, 5).span(Range.closed(6, 10)));
        System.out.println(Range.closed(1, 5).span(Range.closed(7, 10)));
    }
}
