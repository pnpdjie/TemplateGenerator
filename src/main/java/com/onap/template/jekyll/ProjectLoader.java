package com.onap.template.jekyll;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.digester3.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.onap.template.controller.LauncherController;
import com.onap.template.model.Menus;
import com.onap.template.model.Projects;

/**
 * xml转换对象.
 * 
 * @author ywx474563 2017年6月14日
 */
public class ProjectLoader {

  private static final Logger logger = LoggerFactory.getLogger(ProjectLoader.class);

  /**
   * 读取xml文件生成Jekyll项目列表
   * @param xmlPath xml文件路径
   * @return
   */
  public static Projects loadProjects(String xmlPath) {
    
    Digester digester = new Digester();

    digester.addObjectCreate("projects", "com.onap.template.model.Projects");
    digester.addSetProperties("projects");
    digester.addObjectCreate("projects/project", "com.onap.template.model.Project");
    digester.addSetProperties("projects/project");
    digester.addSetNext("projects/project", "addProject");

    Projects projects = null;
    try {
      projects = (Projects) digester.parse(new File(xmlPath));
    } catch (SAXException e) {
      logger.error("转换xml文件出错");
      e.printStackTrace(); 
    } catch (IOException e) {
      logger.error("读取xml文件出错");
    }
    return projects;
  }
}
