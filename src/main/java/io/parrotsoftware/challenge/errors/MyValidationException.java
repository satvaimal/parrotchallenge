package io.parrotsoftware.challenge.errors;

public class MyValidationException extends RuntimeException {

  private MyError myError;

  public MyValidationException(MyError myError) {
    this.myError = myError;
  }

  public MyError getMyError() {
    return this.myError;
  }

}
