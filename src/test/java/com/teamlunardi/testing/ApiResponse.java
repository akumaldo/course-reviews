package com.teamlunardi.testing;

/**
 * Created by akumaldo on 2/15/17.
 */
public class ApiResponse {
  private final int status;
  private final String body;

  public ApiResponse(int status, String body) {
    this.status = status;
    this.body = body;
  }

  public String getBody() {
    return body;
  }


  public int getStatus() {
    return status;
  }
}
