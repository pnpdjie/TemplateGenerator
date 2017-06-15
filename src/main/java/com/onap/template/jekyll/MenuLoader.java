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

/**
 * xml转换对象.
 * 
 * @author ywx474563 2017年6月14日
 */
public class MenuLoader {

  private static final Logger logger = LoggerFactory.getLogger(MenuLoader.class);

  /**
   * 读取xml文件生成菜单数据
   * @param xmlPath xml文件路径
   * @return
   */
  public static Menus loadFromXml(String xmlPath) {
    
    Digester digester = new Digester();

    digester.addObjectCreate("Menus", "com.onap.template.model.Menus");
    digester.addSetProperties("Menus");
    digester.addObjectCreate("Menus/MetaMenu", "com.onap.template.model.MetaMenu");
    digester.addSetProperties("Menus/MetaMenu");
    digester.addSetNext("Menus/MetaMenu", "add");

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
}
