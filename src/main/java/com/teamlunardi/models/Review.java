package com.teamlunardi.models;


public class Review {
  private int id;
  private int courseId;
  private int rating;
  private String comment;

  public Review(int courseId, int rating, String comment) {
    this.courseId = courseId;
    this.rating = rating;
    this.comment = comment;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCourseId() {
    return courseId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
