package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.onap.template.AsynchTester;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
  }

  /**
   * 测试导航名称重复.
   */
  @Test
  public void testNameExist() {
    try {

      // 读取Jekyll项目菜单数据
      Launcher launcher = Launcher.getInstance()
          .init(relativePath + "\\_test_files_menu_generator\\toc_name_exist");
      launcher.loadProject();

      MetaMenu metaMenu = loadedMenus.getMetaMenus().get(0);

      MenuGenerator menuGenerator = new MenuGenerator(metaMenu, loadedMenus,
          relativePath + "config/", launcher) {
        @Override
        public void afterSucceeded(String msg) {
          fail("导航名称重复却创建成功");
        }

        @Override
        public void afterFailed(String msg) {
          assertEquals(this.getTitle(), "执行失败");
        }
      };

      boolean res = menuGenerator.call();
      assertEquals(res, false);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "当前菜单已创建，不能重复创建菜单。");
    }
  }

  /**
   * 测试_config.yml中tocs不存在.
   */
  @Test
  public void testTocsOfConfigNotExist() {
    try {

      // 读取Jekyll项目菜单数据
      Launcher launcher = Launcher.getInstance()
          .init(relativePath + "\\_test_files_menu_generator\\tocs_not_exist");
      launcher.loadProject();

      MetaMenu metaMenu = loadedMenus.getMetaMenus().get(0);

      MenuGenerator menuGenerator = new MenuGenerator(metaMenu, loadedMenus,
          relativePath + "config/", launcher) {
        @Override
        public void afterSucceeded(String msg) {
        }

        @Override
        public void afterFailed(String msg) {
        }
      };
      menuGenerator.call();
    } catch (Exception e) {
      assertEquals(e.getMessage(), "_config.yml文件中未找到参数tocs。");
    }
  }

  @Test
  public void testExecuteSuccess() throws InterruptedException {
    // 读取Jekyll项目菜单数据
    Launcher launcher = Launcher.getInstance();
    launcher.init(relativePath + "\\pnpdjie.github.io");
    launcher.loadProject();

    MetaMenu metaMenu = loadedMenus.getMetaMenus().get(loadedMenus.getMetaMenus().size() - 1);

    MenuGenerator menuGenerator = new MenuGenerator(metaMenu, loadedMenus,
        relativePath + "config/", launcher) {
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
         String indexPath = mdDir.getAbsolutePath() + File.separator +
         metaMenu.getName()
         + File.separator + Constants.JEKYLL_MD_INDEX;
         File indexFile = new File(indexPath);
         if (!indexFile.exists()) {
         fail("docs目录下index.md未创建成功");
         }
        
         List<MetaMenuTemplate> templates = metaMenu.getTemplates();
         int templateSize = templates.size();
         for (int i = 0; i < templateSize; i++) {
         String samplePath = mdDir.getAbsolutePath() + File.separator +
         metaMenu.getName()
         + File.separator + metaMenu.getName() + (i + 1) +
         Constants.JEKYLL_MD_EXTENSION;
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
    // AsynchTester tester =new AsynchTester(menuGenerator);
    // tester.start();
    // tester.test();

    try {
      boolean res = menuGenerator.call();
      menuGenerator.removeTocOfConfig();
      assertEquals(res, true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

}
