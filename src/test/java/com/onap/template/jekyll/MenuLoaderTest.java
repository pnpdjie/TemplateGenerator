package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;

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
    Menus menus = MenuLoader.loadMenus(relativePath + "config/Menus.xml");
    // assertEquals(menus.getMetaMenus().size(), 3);
    assertEquals(menus.getMetaMenus().get(0).getName(), "setup");
    assertEquals(menus.getMetaMenus().get(0).getTemplates().get(0).getPath(), "config/MdTemplate.md");
    assertEquals(menus.getMetaMenus().get(1).getName(), "guides");
    assertEquals(menus.getMetaMenus().get(1).getTemplates().get(0).getPath(), "config/MdTemplate.md");
    assertEquals(menus.getMetaMenus().get(2).getName(), "deploy");
    assertEquals(menus.getMetaMenus().get(2).getTemplates().get(0).getPath(), "config/MdTemplate.md");
  }

  @Test
  public void testAddMenuType() {
    List<MetaMenuTemplate> templates = new ArrayList<MetaMenuTemplate>();
    String savePath1 = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
    String savePath2 = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
    templates.add(new MetaMenuTemplate(savePath1, ""));
    templates.add(new MetaMenuTemplate(savePath2, ""));
    boolean res = MenuLoader.addMenuType(relativePath + "config/Menus.xml", new MetaMenu("a", "a b",templates));
    assertEquals(res, true);
    
    Menus menus = MenuLoader.loadMenus(relativePath + "config/Menus.xml");
    List<MetaMenu>list = menus.getMetaMenus();
    List<MetaMenuTemplate> templates2 = list.get(list.size()-1).getTemplates();
    assertEquals(templates2.size(), 2);
  }

}
