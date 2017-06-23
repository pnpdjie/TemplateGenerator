package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.onap.template.JfxRunner;
import com.onap.template.TestInJfxThread;
import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;
import com.onap.template.ui.ProgressDialog;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.concurrent.Task;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;

/**
 * com.onap.template.jekyll.MenuGenerator单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
@RunWith(JfxRunner.class)
public class MenuGeneratorTest {

  // 测试数据路径
  static String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\";

  // 读取菜单模板
  static Menus loadedMenus = MenuLoader.loadMenus(relativePath + "config/Menus.xml");

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // 读取Jekyll项目菜单数据
    Launcher.getInstance().init(relativePath + "\\pnpdjie.github.io").loadProject();
  }
  
  private void failWrapper(String msg){
    fail(msg);
  }

  private void assertEqualsWrapper(String arg0,String arg1){
    assertEquals(arg0,arg1);
  }

  /**
   * 测试导航名称重复.
   */
  @Test
  @TestInJfxThread
  public void testNameExist() {
    try {
      assertTrue(Platform.isFxApplicationThread());

      MetaMenu metaMenu = loadedMenus.getMetaMenus().get(0);

      MenuGenerator menuGenerator = new MenuGenerator(metaMenu, loadedMenus,
          relativePath + "config/MdTemplate.md") {
        @Override
        public void afterSucceeded(String msg) {
          failWrapper("导航名称重复却创建成功");
        }

        @Override
        public void afterFailed(String msg) {
          assertEqualsWrapper(this.getTitle(), "执行失败");
        }
      };
      Thread thread = new Thread(menuGenerator);
      thread.start();
      thread.join();
    } catch (Exception e) {
      fail("执行出错");
    }
  }


  @Test
  @TestInJfxThread
  public void testExecuteSuccess() {
    try {
      assertTrue(Platform.isFxApplicationThread());

      MetaMenu metaMenu = loadedMenus.getMetaMenus().get(loadedMenus.getMetaMenus().size() - 1);

      MenuGenerator menuGenerator = new MenuGenerator(metaMenu, loadedMenus,
          relativePath + "config/MdTemplate.md") {
        @Override
        public void afterSucceeded(String msg) {
          List<JekyllMenu> listMenu = Launcher.getInstance().loadProject();

          assertEquals(listMenu.size(), 4);
          assertEquals(listMenu.get(3).getName(), metaMenu.getName());

          File dataFile = new File(this.getDataFilePath());
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
        public void afterFailed(String msg) {
          fail("创建导航失败");
        }
      };
      Thread thread = new Thread(menuGenerator);
      thread.start();
      thread.join();
    } catch (Exception e) {
      fail("执行出错");
    }
  }

}
