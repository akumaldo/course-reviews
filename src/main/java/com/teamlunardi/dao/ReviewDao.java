package com.teamlunardi.dao;

import com.teamlunardi.exc.DaoException;
import com.teamlunardi.models.Review;

import java.util.List;

/**
 * Created by akumaldo on 2/15/17.
 */
public interface ReviewDao {
  void add(Review review) throws DaoException;
  List<Review> findAll();
  List<Review> findById(int courseId);
}
