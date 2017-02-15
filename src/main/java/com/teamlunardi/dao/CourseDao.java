package com.teamlunardi.dao;

import com.teamlunardi.exc.DaoException;
import com.teamlunardi.models.Course;
import com.teamlunardi.models.Review;

import java.util.List;

public interface CourseDao {
  void add(Course course) throws DaoException;

  List<Course> findAll();

  Course findById(int id);
}
