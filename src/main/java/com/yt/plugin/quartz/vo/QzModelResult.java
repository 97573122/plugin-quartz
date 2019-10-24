package com.yt.plugin.quartz.vo;

/**
 * 统一接口返回对象
 * @author zhuzhengjie
 * @param <T>
 */
public class QzModelResult<T> extends QzResp {
    /**
     * 自定义协议对象
     */

    protected T model;

    public QzModelResult() {

    }

    public QzModelResult(T model) {
        this.code = QzFwHttpStatus.OK.value();
        this.model = model;
    }


    public void setSuccResp(T model) {
        this.code = QzFwHttpStatus.OK.value();
        this.model = model;
    }



    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }



    @Override
    public String toString() {
        return super.toString()+ ", model=" + model + "]";
    }
}
