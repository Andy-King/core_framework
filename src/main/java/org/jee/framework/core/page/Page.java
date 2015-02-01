package org.jee.framework.core.page;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * 分页类
 * 注:如果此对象要缓存,请实现此接口,不然用memcached时候返回为null
 * @param <T>
 */
public class Page<T> implements Serializable{
	/**
	 * <pre>
	 * @Field   role : 如果此对象要缓存,请实现此接口,不然用memcached时候返回为null
	 * 
	 * @author  name : Andy King
	 * @Create  time : 2012-5-16 : 下午01:48:31
	 * </pre>
	 */
	private static final long serialVersionUID = -1L;
	/**当前页*/
	private int pageNo = 1;
	/**当前页显示记录数*/
	private int pageSize = 15;
	/**当前查询的结果总记录数*/
	private int totalLine;
	/**当前查询的结果总页数*/
	private int totalPages;
	
	/**当前查询的结果开始显示页的索引*/
	private int beginIndex;
	/**当前查询的结果结束显示页的索引*/
	private int endIndex;
	/**当前查询的结果集*/
	private Collection<T> list = Collections.emptyList();

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if(pageNo > 1)
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize > 0)
		this.pageSize = pageSize;
	}

	public int getTotalLine() {
		return totalLine;
	}

	public void setTotalLine(int totalLine) {
		this.totalLine = totalLine;
	}

	public int getTotalPages() {
		totalPages = this.totalLine / this.pageSize;
		
		return totalPages = this.totalLine % this.pageSize == 0 ? this.totalPages : this.totalPages + 1;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public Collection<T> getList() {
		return list;
	}
	
	public void setList(Collection<T> list) {
		this.list = list;
	}

	// ~ Methods -------------------------------------------
	
	public void calculateIndex(){
		
		if(this.totalPages <= 10){
			beginIndex = 1;
			endIndex = this.totalPages;
		}else{
			if(this.pageSize % 2 == 0){
				this.beginIndex = this.pageNo - (this.pageSize / 2 -1);
			}else{
				this.beginIndex = this.pageNo - (this.pageSize / 2);
			}
			this.endIndex = this.pageNo + this.pageSize /2;
			
			if(this.beginIndex < 1){
				this.beginIndex = 1;
				this.endIndex = this.pageSize;
			}
			else if(this.endIndex > this.totalPages){
				this.beginIndex = this.totalPages- this.pageSize + 1;
				this.endIndex = this.totalPages;
			}
			
			
		}
		
		
	}

	public int getFirstResult(){
		return (this.pageNo - 1) * this.pageSize;
	}
	
	public int getMaxResults(){
		return this.pageSize;
	}

	public int getBeginIndex() {
		this.calculateIndex();
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	
}