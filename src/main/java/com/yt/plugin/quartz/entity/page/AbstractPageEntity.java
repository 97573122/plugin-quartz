package com.yt.plugin.quartz.entity.page;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

/**
 * 需要分页的继承这个类
 * @author zhuzhengjie
 */
public class AbstractPageEntity {
	
	private static final long serialVersionUID = -7089454812662142740L;
	
	@ApiModelProperty(required = false)
	@JSONField(serialize=false)
	private Integer currentPage=1; //当前页数
	@ApiModelProperty(required = false)
	@JSONField(serialize=false)
	private Integer pageSize=10;    //每页条数
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
