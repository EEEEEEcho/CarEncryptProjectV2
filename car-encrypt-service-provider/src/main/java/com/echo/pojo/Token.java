package com.echo.pojo;

import com.echo.encrypt.gm.sm9.ResultSignature;

public class Token {
    private String id;
    private Integer Ra;
    private Integer Rb;
    private String text;
    private ResultSignature resultSignature;

    public Token() {
    }

    public Token(String id, Integer ra, Integer rb, String text, ResultSignature resultSignature) {
        this.id = id;
        this.Ra = ra;
        this.Rb = rb;
        this.text = text;
        this.resultSignature = resultSignature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRa() {
        return Ra;
    }

    public void setRa(Integer ra) {
        Ra = ra;
    }

    public Integer getRb() {
        return Rb;
    }

    public void setRb(Integer rb) {
        Rb = rb;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResultSignature getResultSignature() {
        return resultSignature;
    }

    public void setResultSignature(ResultSignature resultSignature) {
        this.resultSignature = resultSignature;
    }
}
