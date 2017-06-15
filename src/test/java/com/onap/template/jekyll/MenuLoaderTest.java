package com.onap.template.jekyll;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.onap.template.Main;
import com.onap.template.model.Menus;

/**
 * 测试MenuLoader类.
 * 
 * @author ywx474563
 *    2017年6月15日
 */
public class MenuLoaderTest {
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testLoadFromXml() {
    Menus menus = MenuLoader.loadMenus(Main.class.getResource("data/Menus.xml").getPath());
    assertEquals(menus.getMdCount(), 3);
    assertEquals(menus.getMdName(), "sample");
    assertEquals(menus.getMetaMenus().size(), 3);
    assertEquals(menus.getMetaMenus().get(0).getName(), "install");
    assertEquals(menus.getMetaMenus().get(1).getName(), "guides");
    assertEquals(menus.getMetaMenus().get(2).getName(), "deploy");
  }

}
