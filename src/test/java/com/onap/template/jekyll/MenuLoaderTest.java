package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;

import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;

import org.junit.Test;

/**
 * MenuLoader单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
public class MenuLoaderTest {
  String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\";

  @Test
  public void testLoadFromXml() {
    Menus menus = MenuLoader.loadMenus(relativePath + "data/Menus.xml");
    assertEquals(menus.getMdCount(), 3);
    assertEquals(menus.getMdName(), "sample");
    // assertEquals(menus.getMetaMenus().size(), 3);
    assertEquals(menus.getMetaMenus().get(0).getName(), "install");
    assertEquals(menus.getMetaMenus().get(1).getName(), "guides");
    assertEquals(menus.getMetaMenus().get(2).getName(), "deploy");
  }

  @Test
  public void testAddMenuType() {
    boolean res = MenuLoader.addMenuType(relativePath + "data/Menus.xml", new MetaMenu("a", "a b"));
    assertEquals(res, true);
  }

}
