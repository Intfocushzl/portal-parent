package com.yonghui.portal.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 分布的工具类
 * @author ki 
 * @param <T>
 */
@SuppressWarnings("serial")
public class Page<T> implements java.io.Serializable{
	
	//-- 分页参数 --//
	protected int page = 1;// 页数
	protected int pageSize = 5;// 显示条数
//	protected int leftCount=3;// 左边显示的条数
//	protected int rigthCount=3;// 右边显示的条数
//	private String forwordName;// 跳转页面
	protected String order = null;//设置排序字段，多个字段用，隔开 格式（name:desc,createDate:asc）
	protected List<T> rows;//取得页内的记录列表.

	protected String orderBy;
	//-- 返回结果 --//
	protected long total = -1;// 总条数

	public Page() {
	}
	
	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	//-- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号如果大于总条数，则当前页定位到总页数
	 */
	public int getPage() {
		if(this.getTotalPages()>-1&&this.page>this.getTotalPages()){		
			this.page=(int) this.getTotalPages();
		}
		return page;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPage(final int page) {
		this.page = page;

		if (page < 1) {
			this.page = 1;
		}
		
	}

	public Page<T> page(final int thePage) {
		setPage(thePage);
		return this;
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((page - 1) * pageSize) + 1;
	}


	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {

		StringBuffer orderBy=new StringBuffer(" order by ");

		orderBy.append(" "+StringUtils.replace(order, ":"," "));	


		this.order = orderBy.toString();
	}

	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(order);
	}



	//-- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getRows() {
		return rows;
	}
	/**
	 * 设置页内的记录列表.
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * 设置总记录数.
	 */
	public void setTotal(final long total) {
		this.total = total;
	}
	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (total < 0) {
			return -1;
		}

		long count = total / pageSize;
		if (total % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (page + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return page + 1;
		} else {
			return page;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (page - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return page - 1;
		} else {
			return page;
		}
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	
	
	
}
