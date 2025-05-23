package com.jieli.jl_bt_ota.model.base;
/* loaded from: classes2.dex */
public class BaseError {
    private int code;
    private String message;
    private int opCode;
    private int subCode;

    public BaseError() {
    }

    public BaseError(int i, String str) {
        this.code = 0;
        this.subCode = i;
        this.message = str;
    }

    public BaseError(int i, int i2, String str) {
        this.code = i;
        this.subCode = i2;
        this.message = str;
    }

    public int getCode() {
        return this.code;
    }

    public BaseError setCode(int i) {
        this.code = i;
        return this;
    }

    public int getSubCode() {
        return this.subCode;
    }

    public BaseError setSubCode(int i) {
        this.subCode = i;
        return this;
    }

    public int getOpCode() {
        return this.opCode;
    }

    public BaseError setOpCode(int i) {
        this.opCode = i;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public BaseError setMessage(String str) {
        this.message = str;
        return this;
    }

    public String toString() {
        return "BaseError{code=" + this.code + ", subCode=" + this.subCode + ", opCode=" + this.opCode + ", message='" + this.message + "'}";
    }
}
