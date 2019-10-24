package com.yt.plugin.quartz.enums;

public enum XmPushStatusEnum {

	NOT_PUSH(0,"未推送"),
	PUSHING(1,"推送中"),
	SUCCESS(2, "推送成功"),
	FAILED(3, "推送失败");

	
	private Integer status;
	private String info;

	private XmPushStatusEnum(Integer status,String info){
		this.status = status;
		this.info = info;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
