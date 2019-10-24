package com.yt.plugin.quartz.enums;

public enum TpyzqApiReturnCodeEnum {

	PUSH_FIAL(1078, "推送失败"),
	PUSH_DATA_IS_NULL(1079, "推送数据为空");
	private final int code;
	private final String desc;

	
	
	private TpyzqApiReturnCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
