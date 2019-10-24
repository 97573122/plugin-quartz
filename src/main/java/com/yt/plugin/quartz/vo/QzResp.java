package com.yt.plugin.quartz.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class QzResp implements Serializable {
	
	private static final long serialVersionUID = 4352740345025493231L;
	
    /**
     * 返回状态码
     */
    protected int code;

    /**
     * 错误信息
     */
    protected String msg;


    public QzResp(int code) {
    	this.code = code;
    }

    public QzResp() {
    	this.code = QzFwHttpStatus.OK.value();
    }

    public QzResp(int code, String errorMsg) {
    	super();
		this.code = code;
		this.msg = errorMsg;
    }

    public QzResp(String errorMsg) {
    	super();
		this.code = -1;
		this.msg = errorMsg;
    }


    public void setErrorCodeAndMsg(int code, String errorMsg) {
    	this.code = code;
		this.msg = errorMsg;
    }
    
    public void setErrorMsgWithDefaultCode(String errorMsg) {
    	this.code = QzErrorCodeEnum.S_100000.getValue();
		this.msg = errorMsg;
    }

    public int getCode() {
    	return code;
    }

    public void setCode(int code) {
    	this.code = code;
    }

    public String getMsg() {
    	return msg;
    }
	
    public void setMsg(String msg) {
    	this.msg = msg;
    }

    @JSONField(serialize=false)
    public boolean isSucc() {
		if (code == QzFwHttpStatus.OK.value()) {
		    return true;
		}
		return false;
    }

    @JSONField(serialize=false)
    public boolean isFail(){
		if(code != QzFwHttpStatus.OK.value()){
			return true;
		}
		return false;
	}

	
    @Override
    public String toString() {
    	return "ModelResult [code=" + code + ", errorMsg=" + msg ;
    }

}
