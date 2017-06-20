package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * com.onap.template.jekyll.MenuGenerator单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
public class MenuGeneratorTest {

  public static MenuGenerator menuGenerator;
  public static Menus loadedMenus;
  public static MetaMenu metaMenu;

  /**
   * 初始化MenuGenerator.
   * @throws Exception 异常
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

    // 测试数据路径
    String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\";
    // 读取菜单模板
    loadedMenus = MenuLoader.loadMenus(relativePath + "config/Menus.xml");
    metaMenu = loadedMenus.getMetaMenus().get(0);

    // 读取Jekyll项目菜单数据
    Launcher.getInstance().init(relativePath + "\\pnpdjie.github.io").loadProject();

    // 初始化菜单生成器
    menuGenerator = new MenuGenerator(metaMenu, loadedMenus, relativePath + "data/MdTemplate.md") {
      @Override
      public void afterSucceeded() {
        List<JekyllMenu> listMenu = Launcher.getInstance().loadProject();

        assertEquals(listMenu.size(), 4);
        assertEquals(listMenu.get(3).getName(), metaMenu.getName());

        File dataFile = new File(menuGenerator.getDataFilePath());
        if (!dataFile.exists()) {
          fail("_data目录下菜单数据文件未创建成功");
        }

        File mdDir = Launcher.getInstance().getMdDir();

        // index.md路径
        String indexPath = mdDir.getAbsolutePath() + File.separator + metaMenu.getName()
            + File.separator + Constants.JEKYLL_MD_INDEX;
        File indexFile = new File(indexPath);
        if (!indexFile.exists()) {
          fail("docs目录下index.md未创建成功");
        }

        List<MetaMenuTemplate> templates = metaMenu.getTemplates();
        int templateSize = templates.size();
        for (int i = 0; i < templateSize; i++) {
          String samplePath = mdDir.getAbsolutePath() + File.separator + metaMenu.getName()
              + File.separator + metaMenu.getName() + (i + 1) + Constants.JEKYLL_MD_EXTENSION;
          File sampleFile = new File(samplePath);
          if (!sampleFile.exists()) {
            fail("docs目录下" + metaMenu.getName() + (i + 1) + ".md未创建成功");
          }
        }
      }

      @Override
      public void afterFailed() {
        // TODO Auto-generated method stub

      }
    };

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
  public void testExecute() {
    try {
      Thread th = new Thread(menuGenerator);
      th.start();
    } catch (Exception e) {
      fail("执行出错");
    }

  }
}
