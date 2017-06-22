package com.onap.template.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

/**
 * Project单元测试.
 * 
 * @author ywx474563 2017年6月21日
 */
public class ProjectTest {

  @Test
  public void testProject() {
    Project project = new Project();

    assertNull(project.getPath());
    assertNull(project.getLoadDate());
  }

  @Test
  public void testProjectStringString() {
    Project project = new Project("a", "b");

    assertEquals(project.getPath(), "a");
    assertEquals(project.getLoadDate(), "b");
  }

  @Test
  public void testProjectStringDate() {
    Date date = new Date();
    Project project = new Project("a", date);

    assertEquals(project.getPath(), "a");
    assertEquals(project.getLoadDate(), project.formatter.format(date));
  }

}
