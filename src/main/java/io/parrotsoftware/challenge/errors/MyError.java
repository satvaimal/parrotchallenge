package io.parrotsoftware.challenge.errors;

public class MyError {

  private String field;
  private String message;

  public MyError(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return this.field;
  }

  public String getMessage() {
    return this.message;
  }

}
