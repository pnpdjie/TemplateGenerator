package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;

import com.onap.template.model.Project;
import com.onap.template.model.Projects;

import java.util.Date;

import org.junit.Test;

/**
 * ProjectLoader单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
public class ProjectLoaderTest {
  String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\";
  String projectXmlPath = relativePath + "\\config\\projects.xml";

  @Test
  public void testLoadProjects() {
    Projects projects = ProjectLoader.loadProjects(projectXmlPath);
    // assertEquals(projects.getProjects().size(), 2);
    assertEquals(projects.getProjects().get(1).getPath(), "D:\\git\\pnpdjie.github.io");
    assertEquals(projects.getProjects().get(0).getPath(),
        "D:\\JAVA\\workspace\\TemplateGenerator\\_test_jekyll_project\\pnpdjie.github.io");
  }

  @Test
  public void testAddProject() {
    boolean res = ProjectLoader.addProject(projectXmlPath, new Project("c:/", new Date()));
    assertEquals(res, true);

    Projects projects = ProjectLoader.loadProjects(projectXmlPath);
    assertEquals(projects.getProjects().size(), 3);
    
    //重复添加系统路径，只是替换时间
    res = ProjectLoader.addProject(projectXmlPath, new Project("c:/", new Date()));
    assertEquals(res, true);

    projects = ProjectLoader.loadProjects(projectXmlPath);
    assertEquals(projects.getProjects().size(), 3);
  }

}
