package com.onap.template.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * MetaMenu单元测试.
 * 
 * @author ywx474563 2017年6月21日
 */
public class MetaMenuTest {

  /**
   * 测试MetaMenu无参构造方法.
   */
  @Test
  public void testMetaMenu() {
    MetaMenu menu = new MetaMenu();

    assertNull(menu.getName());
    assertNull(menu.getDesc());
    assertNotNull(menu.getTemplates());
    assertEquals(menu.getTemplates().size(), 0);
  }

  /**
   * 测试MetaMenu有参构造方法.
   */
  @Test
  public void testMetaMenuHasParams() {
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    templates.add(new MetaMenuTemplate());
    MetaMenu menu = new MetaMenu("a", "b", templates);

    assertEquals(menu.getName(), "a");
    assertEquals(menu.getDesc(), "b");
    assertEquals(menu.getTemplates(), templates);
    assertEquals(menu.getTemplates().size(), 1);
  }

  /**
   * 测试MetaMenu的setter方法.
   */
  @Test
  public void testSetter() {
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    templates.add(new MetaMenuTemplate());
    templates.add(new MetaMenuTemplate());
    MetaMenu menu = new MetaMenu();
    menu.setName("a");
    menu.setDesc("b");
    menu.setTemplates(templates);

    assertEquals(menu.getName(), "a");
    assertEquals(menu.getDesc(), "b");
    assertEquals(menu.getTemplates(), templates);
    assertEquals(menu.getTemplates().size(), 2);
  }

  /**
   * 测试Menus的add方法.
   */
  @Test
  public void testAdd() {
    MetaMenu menu = new MetaMenu();
    MetaMenuTemplate template = new MetaMenuTemplate("a","b");
    menu.add(template);
    
    assertEquals(menu.getTemplates().size(), 1);
    assertEquals(menu.getTemplates().get(0).getPath(), "a");
    assertEquals(menu.getTemplates().get(0).getUploadPath(), "b");
  }

}
