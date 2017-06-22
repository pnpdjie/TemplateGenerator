
package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.onap.template.model.JekyllMenu;

import java.io.File;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * com.onap.template.jekyll.Launcher单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LauncherTest {
  static Launcher launcher;
  private String jekyllPath = System.getProperty("user.dir")
      + "\\_test_jekyll_project\\pnpdjie.github.io";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    PropertyConfigurator.configure("config\\log4j.properties");
    launcher = Launcher.getInstance();
  }

  /**
   * 测试getInstance.
   */
  @Test
  public void testGetInstance() {

    assertNotNull(launcher);
    assertEquals(launcher.getProjectPath(), null);
    assertEquals(launcher.getConfigFile(), null);
    assertEquals(launcher.getDataDir(), null);
    assertEquals(launcher.getMdDir(), null);
    assertEquals(launcher.getListMenu(), null);
  }

  /**
   * 测试init.
   */
  @Test
  public void testInit() {
    launcher.init(jekyllPath);

    assertNotNull(launcher);
    assertEquals(launcher.getProjectPath(), jekyllPath);
    assertEquals(launcher.getConfigFile(), null);
    assertEquals(launcher.getDataDir(), null);
    assertEquals(launcher.getMdDir(), null);
    assertEquals(launcher.getListMenu(), null);
  }

  /**
   * 测试LoadProject.
   */
  @Test
  public void testLoadProject() {
    final List<JekyllMenu> res = launcher.init(jekyllPath).loadProject();

    assertNotNull(launcher);
    assertEquals(launcher.getProjectPath(), jekyllPath);
    assertEquals(launcher.getConfigFile().getAbsolutePath(),
        jekyllPath + File.separator + Constants.JEKYLL_CONFIG_FILE);
    assertEquals(launcher.getDataDir().getAbsolutePath(),
        jekyllPath + File.separator + Constants.JEKYLL_DATA_DIR);
    assertEquals(launcher.getMdDir().getAbsolutePath(),
        jekyllPath + File.separator + Constants.JEKYLL_MD_DIR);
    assertNotNull(launcher.getListMenu());
    // assertEquals(res.size(), 3);
    assertEquals(res.get(0).getName(), "home");
    assertEquals(res.get(1).getName(), "guides");
    assertEquals(res.get(2).getName(), "setup");
  }

}
