package com.echo.pojo;

public enum HttpStatus {
    SUCCESS("success",200),NOTFOUND("not found",404);
    public final String tip;
    public final int code;
    HttpStatus(String tip,int code){
        this.tip = tip;
        this.code = code;
    }
}
