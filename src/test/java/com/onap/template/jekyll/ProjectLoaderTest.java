package com.onap.template.jekyll;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.onap.template.Main;
import com.onap.template.model.Menus;
import com.onap.template.model.Projects;

/**
 * ProjectLoader单元测试.
 * 
 * @author ywx474563
 *    2017年6月15日
 */
public class ProjectLoaderTest {
  String relativePath =System.getProperty("user.dir")+"\\_test_jekyll_project\\";

  @Test
  public void testLoadProjects() {
    Projects projects = ProjectLoader.loadProjects(relativePath+"\\config\\projects.xml");
    assertEquals(projects.getProjects().size(), 2);
    assertEquals(projects.getProjects().get(0).getPath(), "D:\\git\\pnpdjie.github.io");
    assertEquals(projects.getProjects().get(1).getPath(), "D:\\JAVA\\workspace\\TemplateGenerator\\_test_jekyll_project\\pnpdjie.github.io");
  }

}
