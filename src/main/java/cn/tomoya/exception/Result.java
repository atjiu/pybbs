package cn.tomoya.exception;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class Result<T> {

    private int code;
    private String description;
    private T detail;

    public static Result success() {
        return success(null);
    }

    public static Result success(Object detail) {
        Result result = new Result();
        result.setCode(200);
        result.setDescription("success");
        result.setDetail(detail);
        return result;
    }

    public static Result error() {
        return error(null);
    }

    public static Result error(String description) {
        return error(ErrorCode.error, description);
    }

    public static Result error(int code, String description) {
        Result result = new Result();
        result.setCode(code);
        result.setDescription(description);
        result.setDetail(null);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }
}
