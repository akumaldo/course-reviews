package com.teamlunardi;

import static org.junit.Assert.*;

import com.google.gson.Gson;

import com.teamlunardi.dao.CourseDao;
import com.teamlunardi.dao.Sql2oCourseDao;
import com.teamlunardi.dao.Sql2oReviewDao;
import com.teamlunardi.models.Course;
import com.teamlunardi.models.Review;
import com.teamlunardi.testing.ApiClient;
import com.teamlunardi.testing.ApiResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Spark;


public class ApiTest {

  public static final String PORT =   "4568";
  public static final String TEST_DATA_SOURCE = "jdbc:h2:mem:testing";
  private Connection conn;
  private ApiClient client;
  private Gson gson;
  private Sql2oCourseDao courseDao;
  private Sql2oReviewDao reviewDao;

  @BeforeClass
  public static void startServer(){
    String[] args = {PORT, TEST_DATA_SOURCE};
    Api.main(args);
  }

  @Before
  public void setUp() throws Exception {
    Sql2o sql2o = new Sql2o(TEST_DATA_SOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "","");
    courseDao = new Sql2oCourseDao(sql2o);
    reviewDao = new Sql2oReviewDao(sql2o);
    conn = sql2o.open();
    client  = new ApiClient("http://localhost:" + PORT);
    gson = new Gson();
  }

  @AfterClass
  public static void stopServer(){
    Spark.stop();
  }

  @Test
  public void addingCoursesReturnsCreatedStatus() throws Exception {
    Map<String,String> values = new HashMap<>();
    values.put("name", "test");
    values.put("url","http://test.com");
    ApiResponse res = client.request("POST", "/courses", gson.toJson(values));

    assertEquals(201, res.getStatus());
  }

  @Test
  public void multipleReviewsReturnedByProvidedCourse() throws Exception {
    Course course = createNewTest();
    courseDao.add(course);
    reviewDao.add(new Review(course.getId(),5,"Test comment 1"));
    reviewDao.add(new Review(course.getId(),4,"Test comment 2"));
    reviewDao.add(new Review(course.getId(),3,"Test comment 4"));

    List<Review> reviews = reviewDao.findById(course.getId());

    assertEquals(3, reviews.size());
  }

  @Test
  public void addingReviewsReturnsNotFoundStatus() throws Exception {
    Course course = createNewTest();
    courseDao.add(course);
    Map<String,Object> values = new HashMap<>();
    values.put("rating", 5);
    values.put("comment","great!!!");
    ApiResponse res = client.request("POST",
            String.format("/courses/%d/reviews", course.getId()), gson.toJson(values));
    assertEquals(200, res.getStatus());
  }

  @Test
  public void addingReviewsToUnknownCourseThrowsException() throws Exception {
    Map<String,Object> values = new HashMap<>();
    values.put("rating", 5);
    values.put("comment","great!!!");
    ApiResponse res = client.request("POST",
           "/courses/42/reviews", gson.toJson(values));
    assertEquals(500, res.getStatus());
  }


  @Test
  public void courseCanBeAccessedById() throws Exception {
    Course course = createNewTest();
    courseDao.add(course);

    ApiResponse res = client.request("GET",
        "/courses/"
        + course.getId());

    Course retrieved = gson.fromJson(res.getBody(), Course.class);

    assertEquals(course, retrieved);
  }

  @Test
  public void missingCoursesReturnNotFoundStatus() throws Exception {
    ApiResponse res = client.request("GET", "/courses/42");

    assertEquals(404, res.getStatus());
  }


  @After
  public void tearDown() throws Exception {
    conn.close();
  }


  private static Course createNewTest() {
    return new Course("test", "http://test.com");
  }

}