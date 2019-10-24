package com.yt.plugin.quartz.exception;

public class QuartzException extends RuntimeException {


    public QuartzException(){

    }

    public QuartzException(String message){
        super(message);
    }
}
