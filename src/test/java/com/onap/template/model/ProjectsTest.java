package com.onap.template.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Projects单元测试
 * 
 * @author ywx474563
 *    2017年6月21日
 */
public class ProjectsTest {

  /**
   * 测试MetaMenu无参构造方法.
   */
  @Test
  public void testProjects() {
    Projects projects = new Projects();

    assertNotNull(projects.getProjects());
    assertEquals(projects.getProjects().size(), 0);
  }

  /**
   * 测试Menus的setter方法.
   */
  @Test
  public void testSetter() {
    Projects projects = new Projects();
    List<Project> projectList = new ArrayList<Project>();
    projectList.add(new Project("a", "b"));
    projects.setProjects(projectList);
    assertEquals(projects.getProjects(), projectList);
    assertEquals(projects.getProjects().size(), 1);
    assertEquals(projects.getProjects().get(0).getPath(), "a");
    assertEquals(projects.getProjects().get(0).getLoadDate(), "b");
  }

  /**
   * 测试Menus的add方法.
   */
  @Test
  public void testAddProject() {
    Projects projects = new Projects();
    Project project=new Project("a", "b");
    projects.addProject(project);
    assertEquals(projects.getProjects().size(), 1);
    assertEquals(projects.getProjects().get(0).getPath(), "a");
    assertEquals(projects.getProjects().get(0).getLoadDate(), "b");
  }

}
