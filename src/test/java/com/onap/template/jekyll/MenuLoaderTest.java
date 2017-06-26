package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

/**
 * MenuLoader单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
public class MenuLoaderTest {
  String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\";

  @Test
  public void testLoadFromXmlFail() {
    Menus menus = MenuLoader.loadMenus(relativePath + "config/Menus1.xml");

    assertEquals(menus, null);
  }

  @Test
  public void testLoadFromXmlSuccess() {
    Menus menus = MenuLoader.loadMenus(relativePath + "config/Menus.xml");
    // assertEquals(menus.getMetaMenus().size(), 3);
    assertEquals(menus.getMetaMenus().get(0).getName(), "setup");
    assertEquals(menus.getMetaMenus().get(0).getTemplates().get(0).getPath(),
        "config/MdTemplate.md");
    assertEquals(menus.getMetaMenus().get(1).getName(), "guides");
    assertEquals(menus.getMetaMenus().get(1).getTemplates().get(0).getPath(),
        "config/MdTemplate.md");
    assertEquals(menus.getMetaMenus().get(2).getName(), "deploy");
    assertEquals(menus.getMetaMenus().get(2).getTemplates().get(0).getPath(),
        "config/MdTemplate.md");
  }

  @Test
  public void testAddMenuTypeFail() {
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    String savePath1 = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
    String savePath2 = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
    templates.add(new MetaMenuTemplate(savePath1, ""));
    templates.add(new MetaMenuTemplate(savePath2, ""));
    try {
      MenuLoader.addMenuType(relativePath + "config/Menus1.xml",
          new MetaMenu("a", "a b", templates));
      fail("Menus.xml增加元素失败测试不成功");
    } catch (Exception e) {
      assertNotNull(e);
    }
  }

  @Test
  public void testAddMenuTypeSuccess() {
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    String savePath1 = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
    String savePath2 = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
    templates.add(new MetaMenuTemplate(savePath1, ""));
    templates.add(new MetaMenuTemplate(savePath2, ""));
    try {
      MenuLoader.addMenuType(relativePath + "config/Menus.xml",
          new MetaMenu("test", "test 1", templates));
      
      Menus menus = MenuLoader.loadMenus(relativePath + "config/Menus.xml");
      List<MetaMenu> list = menus.getMetaMenus();
      assertEquals(list.size(), 5);
      List<MetaMenuTemplate> addTemplates = list.get(list.size() - 1).getTemplates();
      assertEquals(addTemplates.size(), 2);
      
      MenuLoader.removeMenuType(relativePath + "config/Menus.xml", "test");

      menus = MenuLoader.loadMenus(relativePath + "config/Menus.xml");
      list = menus.getMetaMenus();
      assertEquals(list.size(), 4);
      
    } catch (Exception e) {
      fail("Menus.xml文件写入失败");
    }
  }

}
