package com.crud.task.exception;

public class CustomErrorExceptionHandler extends RuntimeException {

  public CustomErrorExceptionHandler(String msg) {
    super(msg);
  }
}
