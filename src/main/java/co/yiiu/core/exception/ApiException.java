package co.yiiu.core.exception;

import co.yiiu.core.ErrorCodeConstant;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class ApiException extends Exception {

  private int code;
  private String message;

  public ApiException(String message) {
    this.code = ErrorCodeConstant.error;
    this.message = message;
  }

  public ApiException(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
