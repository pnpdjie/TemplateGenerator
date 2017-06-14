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

  public MenuLoader() {

  }

  /**
   * 读取xml文件生成菜单数据
   * @param xml
   * @return
   */
  public Menus loadFromXml(File xml) {
    Digester digester = new Digester();

    digester.addObjectCreate("menus", "com.onap.template.model.Menus");
    digester.addSetProperties("metamenu");
    digester.addObjectCreate("menus/metamenu", "com.onap.template.model.MetaMenu");
    digester.addSetProperties("menus/metamenu");
    digester.addSetNext("menus/metamenu", "add");

    Menus menus = null;
    try {
      menus = (Menus) digester.parse(xml);
    } catch (SAXException e) {
      logger.error("转换xml文件出错");
    } catch (IOException e) {
      logger.error("读取xml文件出错");
    }
    return menus;
  }
}
