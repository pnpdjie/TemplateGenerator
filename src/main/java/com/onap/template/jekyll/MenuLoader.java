package com.onap.template.jekyll;

import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;

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
public class MenuLoader {

  private static final Logger logger = LoggerFactory.getLogger(MenuLoader.class);

  /**
   * 读取xml文件生成菜单数据.
   * 
   * @param xmlPath
   *          xml文件路径
   * @return 界面菜单项
   */
  public static Menus loadMenus(String xmlPath) {

    Digester digester = new Digester();

    digester.addObjectCreate("Menus", "com.onap.template.model.Menus");
    digester.addSetProperties("Menus");
    digester.addObjectCreate("Menus/MetaMenu", "com.onap.template.model.MetaMenu");
    digester.addSetProperties("Menus/MetaMenu");
    digester.addSetNext("Menus/MetaMenu", "add");
    digester.addObjectCreate("Menus/MetaMenu/MetaMenuTemplate",
        "com.onap.template.model.MetaMenuTemplate");
    digester.addSetProperties("Menus/MetaMenu/MetaMenuTemplate");
    digester.addSetNext("Menus/MetaMenu/MetaMenuTemplate", "add");

    Menus menus = null;
    try {
      menus = (Menus) digester.parse(new File(xmlPath));
    } catch (SAXException e) {
      logger.error("转换xml文件出错");
      e.printStackTrace();
    } catch (IOException e) {
      logger.error("读取xml文件出错");
    }
    return menus;
  }

  /**
   * 在xml文件中增加导航模板.
   * 
   * @param xmlPath
   *          xml文件路径
   * @param menu
   *          Jekyll导航模板
   */
  public static void addMenuType(String xmlPath, MetaMenu menu) {
    try {
      // 读取Menus.xml文件
      Document doc = new SAXReader().read(new File(xmlPath));

      // 添加标签
      Element rootElem = doc.getRootElement();
      Element menuElem = rootElem.addElement("MetaMenu");

      // 增加属性
      menuElem.addAttribute("name", menu.getName());
      menuElem.addAttribute("desc", menu.getDesc());

      for (MetaMenuTemplate template : menu.getTemplates()) {
        // 增加MetaMenuTemplate
        Element templateElem = menuElem.addElement("MetaMenuTemplate");

        // 增加MetaMenuTemplate属性
        templateElem.addAttribute("path", template.getPath());
        templateElem.addAttribute("uploadPath", template.getUploadPath());
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

    } catch (DocumentException e) {
      logger.error("Menus.xml文件读取出错");
      throw new RuntimeException("Menus.xml文件读取出错");
    } catch (FileNotFoundException e) {
      logger.error("Menus.xml文件未找到");
      throw new RuntimeException("Menus.xml文件未找到");
    } catch (UnsupportedEncodingException e) {
      logger.error("Menus.xml文件编码格式错误");
      throw new RuntimeException("Menus.xml文件编码格式错误");
    } catch (IOException e) {
      logger.error("Menus.xml文件写入出错");
      throw new RuntimeException("Menus.xml文件写入出错");
    }
  }
  
  /**
   * 删除对应的导航.
   * @param xmlPath xml文件路径
   * @param menuName 导航简称
   */
  public static void removeMenuType(String xmlPath, String menuName) {
    try {
      // 读取Menus.xml文件
      Document doc = new SAXReader().read(new File(xmlPath));

      // 添加标签
      Element rootElem = doc.getRootElement();

      List<Element> elemList = rootElem.elements();
      for (Element elem : elemList) {
        Attribute nameAttr = elem.attribute("name");
        if (StringUtils.equalsIgnoreCase(nameAttr.getData().toString(), menuName)) {
          rootElem.remove(elem);
          break;
        }
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

    } catch (DocumentException e) {
      logger.error("Menus.xml文件读取出错");
      throw new RuntimeException("Menus.xml文件读取出错");
    } catch (FileNotFoundException e) {
      logger.error("Menus.xml文件未找到");
      throw new RuntimeException("Menus.xml文件未找到");
    } catch (UnsupportedEncodingException e) {
      logger.error("Menus.xml文件编码格式错误");
      throw new RuntimeException("Menus.xml文件编码格式错误");
    } catch (IOException e) {
      logger.error("Menus.xml文件写入出错");
      throw new RuntimeException("Menus.xml文件写入出错");
    }
  }
}
