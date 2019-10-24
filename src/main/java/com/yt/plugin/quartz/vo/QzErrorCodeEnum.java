package com.yt.plugin.quartz.vo;

/**
 * 功能描述：错误码定义
 */
public enum QzErrorCodeEnum {
	/** 成功 */
	OK(0, true),

	// 系统公共模块配置 1开头
	/** 系统错误 */
	S_100000(10000, true),
	/** 传入对象不能为空 */
	S_100001(10001, true),
	/** 密码不能为空 */
	S_100002(10002, true),
	/** 参数中存在重复数据 */
	S_100003(10003, true),
	/** 用户登录失效 */
	S_100016(10016, true),
	/** 用户重复登录，被踢出 */
	S_100017(10017, true),
	/** 用户访问参数非法 */
	S_10018(10018, true),
	/** APP类型错误 */
	S_10019(10019, true),
	/** 客户端类型错误 */
	S_10020(10020, true);
	/**
	 * 错误码
	 */
	int code;

	/**
	 * 是否系统模块
	 */
	boolean isSysModule = false;

	QzErrorCodeEnum(int code, boolean isSysModule) {
		this.code = code;
		this.isSysModule = isSysModule;
	}

	/**
	 * 获取枚举值
	 * 
	 * @return int
	 */
	public int getValue() {
		return code;
	}

}
