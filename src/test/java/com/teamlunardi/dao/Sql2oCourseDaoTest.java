package com.teamlunardi.dao;

import static org.h2.command.CommandInterface.RUNSCRIPT;
import static org.junit.Assert.*;

import com.teamlunardi.models.Course;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 * Created by akumaldo on 2/15/17.
 */
public class Sql2oCourseDaoTest {

  private Sql2oCourseDao dao;
  private Connection conn;


  private static Course createNewTest() {
    return new Course("test", "http://test.com");
  }

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT= RUNSCRIPT from 'classpath:db/init.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "","");
    dao = new Sql2oCourseDao(sql2o);
    //keep connection open through the entire test
    conn = sql2o.open();
  }


  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void existingCoursesCanBeFoundById() throws Exception {
    Course course = createNewTest();

    dao.add(course);

    Course courseFind = dao.findById(course.getId());

    assertEquals(course, courseFind);
  }

  @Test
  public void addingCourseSetsId() throws Exception {
    Course course = createNewTest();

    int originalCourseId = course.getId();

    dao.add(course);

    assertNotEquals(originalCourseId, course.getId());
  }

  @Test
  public void addedCoursesAreReturnedFromFindAll() throws Exception {
    Course course = createNewTest();

    dao.add(course);
    assertEquals(1,dao.findAll().size());
  }

  @Test
  public void noCoursesReturnsEmpty() throws Exception {
    assertEquals(0, dao.findAll().size());
  }

}