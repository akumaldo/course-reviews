package com.teamlunardi.exc;

/**
 * Created by akumaldo on 2/15/17.
 */
public class DaoException extends Exception {

  private final Exception originalException;

  public DaoException(Exception originalException, String message){
    super(message);
    this.originalException = originalException;

  }

}
