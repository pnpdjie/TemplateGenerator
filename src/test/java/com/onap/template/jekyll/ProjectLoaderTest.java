package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.onap.template.model.Project;
import com.onap.template.model.Projects;

import java.util.Date;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

/**
 * ProjectLoader单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
public class ProjectLoaderTest {
  String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\";
  String projectXmlPath = relativePath + "\\config\\projects.xml";

  @Test
  public void testLoadProjectsFail() {
    Projects projects = ProjectLoader.loadProjects(projectXmlPath + "1");

    assertNull(projects);
  }

  @Test
  public void testLoadProjectsSuccess() {
    Projects projects = ProjectLoader.loadProjects(projectXmlPath);

    assertEquals(projects.getProjects().get(1).getPath(), "D:\\git\\pnpdjie.github.io");
    assertEquals(projects.getProjects().get(0).getPath(),
        "D:\\JAVA\\workspace\\TemplateGenerator\\_test_jekyll_project\\pnpdjie.github.io");
  }

  @Test
  public void testAddProjectFail() {
    try {
      ProjectLoader.addProject(projectXmlPath + "1", new Project("c:/", new Date()));

      fail("projects.xml增加元素失败测试不成功");
    } catch (Exception e) {
      assertNotNull(e);
    }
  }

  @Test
  public void testAddProjectSuccess() {
    try {
      ProjectLoader.addProject(projectXmlPath, new Project("c:/", new Date()));

      Projects projects = ProjectLoader.loadProjects(projectXmlPath);
      assertEquals(projects.getProjects().size(), 3);

      // 重复添加系统路径，只是替换时间
      ProjectLoader.addProject(projectXmlPath, new Project("c:/", new Date()));

      projects = ProjectLoader.loadProjects(projectXmlPath);
      assertEquals(projects.getProjects().size(), 3);
    } catch (Exception e) {
      fail("projects.xml文件写入失败");
    }
  }

}
