package com.onap.template.jekyll;

import com.onap.template.model.Project;
import com.onap.template.model.Projects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * xml转换对象.
 * 
 * @author ywx474563 2017年6月14日
 */
public class ProjectLoader {

  private static final Logger logger = LoggerFactory.getLogger(ProjectLoader.class);

  /**
   * 读取xml文件生成Jekyll项目列表.
   * 
   * @param xmlPath
   *          xml文件路径
   * @return Jekyll项目列表
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

  /**
   * 在xml文件中增加Jekyll项目路径.
   * 
   * @param xmlPath xml文件路径
   * @param project Jekyll项目
   * @return 执行结果
   */
  public static boolean addProject(String xmlPath, Project project) {
    try {
      // 判断路径是否已存在
      Projects projects = loadProjects(xmlPath);
      boolean exist = false;
      for (Project p : projects.getProjects()) {
        if (StringUtils.equalsIgnoreCase(p.getPath(), project.getPath())) {
          exist = true;
          break;
        }
      }

      // 读取Menus.xml文件
      Document doc = new SAXReader().read(new File(xmlPath));

      // 添加标签
      Element rootElem = doc.getRootElement();
      Element projectElem;
      if (exist) {
        @SuppressWarnings("unchecked")
        List<Element> elemList = rootElem.elements();
        for (Element elem : elemList) {
          Attribute pathAttr = elem.attribute("path");
          Attribute loadDateAttr = elem.attribute("loadDate");
          if (StringUtils.equalsIgnoreCase(pathAttr.getData().toString(), project.getPath())) {
            loadDateAttr.setValue(project.getLoadDate());
            break;
          }
        }
      } else {
        projectElem = rootElem.addElement("project");

        // 增加属性
        projectElem.addAttribute("path", project.getPath());
        projectElem.addAttribute("loadDate", project.getLoadDate());

      }

      // 指定文件输出的位置
      FileOutputStream out = new FileOutputStream(xmlPath);

      // 指定文本的写出的格式：
      OutputFormat format = OutputFormat.createPrettyPrint(); // 漂亮格式：有空格换行
      format.setEncoding(Constants.ENCODING);

      // 创建写出对象
      XMLWriter writer = new XMLWriter(out, format);

      // 写出Document对象
      writer.write(doc);

      // 关闭流
      writer.close();

      return true;
    } catch (DocumentException e) {
      logger.error("projects.xml文件读取出错");
      return false;
    } catch (FileNotFoundException e) {
      logger.error("projects.xml文件未找到");
      return false;
    } catch (UnsupportedEncodingException e) {
      logger.error("projects.xml文件不支持utf-8编码");
      return false;
    } catch (IOException e) {
      logger.error("projects.xml文件写入出错");
      return false;
    } catch (ClassCastException e) {
      logger.error("projects.xml文件写入出错");
      return false;
    }
  }
}
