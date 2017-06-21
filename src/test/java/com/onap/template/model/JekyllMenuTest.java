package com.onap.template.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Test;

/**
 * JekyllMenu单元测试.
 * 
 * @author ywx474563 2017年6月21日
 */
public class JekyllMenuTest {

  /**
   * 测试JekyllMenu构造方法.
   */
  @Test
  public void testJekyllMenu() {
    JekyllMenu jekyllMenu = new JekyllMenu();

    assertNull(jekyllMenu.getName());
    assertNull(jekyllMenu.getDesc());
    assertNull(jekyllMenu.getLeftTreePath());
    assertNull(jekyllMenu.getLeftTreeFile());
    assertNull(jekyllMenu.getIndexPath());
    assertNull(jekyllMenu.getIndexFile());
    assertEquals(jekyllMenu.isCreated(), false);
  }

  /**
   * 测试jekyllMenu的setter方法.
   */
  @Test
  public void testSetter() {
    JekyllMenu jekyllMenu = new JekyllMenu();
    jekyllMenu.setName("a");
    jekyllMenu.setDesc("b");

    jekyllMenu.setLeftTreePath("c:/");
    File leftTreeFile = new File("c:/");
    jekyllMenu.setLeftTreeFile(leftTreeFile);

    jekyllMenu.setIndexPath("d:/");
    File indexFile = new File("d:/");
    jekyllMenu.setIndexFile(indexFile);
    
    jekyllMenu.setCreated(true);

    assertEquals(jekyllMenu.getName(), "a");
    assertEquals(jekyllMenu.getDesc(), "b");
    assertEquals(jekyllMenu.getLeftTreePath(), "c:/");
    assertEquals(jekyllMenu.getLeftTreeFile(), leftTreeFile);
    assertEquals(jekyllMenu.getIndexPath(), "d:/");
    assertEquals(jekyllMenu.getIndexFile(), indexFile);
    assertEquals(jekyllMenu.isCreated(), true);
  }

}
