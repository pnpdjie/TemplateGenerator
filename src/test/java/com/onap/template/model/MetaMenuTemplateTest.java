package com.onap.template.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * MetaMenuTemplate单元测试.
 * 
 * @author ywx474563 2017年6月21日
 */
public class MetaMenuTemplateTest {

  /**
   * 测试MetaMenuTemplate无参构造方法.
   */
  @Test
  public void testMetaMenuTemplate() {
    MetaMenuTemplate template = new MetaMenuTemplate();

    assertNull(template.getPath());
    assertNull(template.getUploadPath());
  }

  /**
   * 测试MetaMenuTemplate有参构造方法.
   */
  @Test
  public void testMetaMenuTemplateHasParams() {
    MetaMenuTemplate template = new MetaMenuTemplate("a","b");

    assertEquals(template.getPath(), "a");
    assertEquals(template.getUploadPath(), "b");
  }

  /**
   * 测试MetaMenuTemplate的setter方法.
   */
  @Test
  public void testSetter() {
    MetaMenuTemplate template = new MetaMenuTemplate();
    template.setPath("a");
    template.setUploadPath("b");

    assertEquals(template.getPath(), "a");
    assertEquals(template.getUploadPath(), "b");
  }

}
