package com.onap.template.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Menus单元测试.
 * 
 * @author ywx474563 2017年6月21日
 */
public class MenusTest {

  /**
   * 测试Menus初始化后metaMenus不为空.
   */
  @Test
  public void testMenus() {
    Menus menus = new Menus();
    assertNotNull(menus.getMetaMenus());
    assertEquals(menus.getMetaMenus().size(), 0);
  }

  /**
   * 测试Menus的setter方法.
   */
  @Test
  public void testSetter() {
    Menus menus = new Menus();
    List<MetaMenu> metaMenus = new ArrayList<MetaMenu>();
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    metaMenus.add(new MetaMenu("a", "b", templates));
    menus.setMetaMenus(metaMenus);
    assertEquals(menus.getMetaMenus(), metaMenus);
    assertEquals(menus.getMetaMenus().size(), 1);
    assertEquals(menus.getMetaMenus().get(0).getName(), "a");
    assertEquals(menus.getMetaMenus().get(0).getDesc(), "b");
    assertEquals(menus.getMetaMenus().get(0).getTemplates(), templates);
  }

  /**
   * 测试Menus的add方法.
   */
  @Test
  public void testAdd() {
    Menus menus = new Menus();
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    MetaMenu menu = new MetaMenu("a", "b", templates);
    menus.add(menu);
    assertEquals(menus.getMetaMenus().size(), 1);
    assertEquals(menus.getMetaMenus().get(0).getName(), "a");
    assertEquals(menus.getMetaMenus().get(0).getDesc(), "b");
    assertEquals(menus.getMetaMenus().get(0).getTemplates(), templates);
  }

}
