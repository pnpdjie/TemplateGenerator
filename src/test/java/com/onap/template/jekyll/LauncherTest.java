
package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;

import com.onap.template.model.JekyllMenu;

import java.util.List;

import org.junit.Test;

/**
 * com.onap.template.jekyll.Launcher单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
public class LauncherTest {

  //
  // @Test
  // public void testHasConfigFile() {
  // boolean res = launcher.hasConfigFile();
  // assertEquals(res, true);
  // }
  //
  // @Test
  // public void testHasDataDir() {
  // boolean res = launcher.hasDataDir();
  // assertEquals(res, true);
  // }
  //
  // @Test
  // public void testHasMdDir() {
  // boolean res = launcher.hasMdDir();
  // assertEquals(res, true);
  // }

  @Test
  public void testLoadProject() {
    List<JekyllMenu> res = Launcher.getInstance()
        .init(System.getProperty("user.dir") + "\\_test_jekyll_project\\pnpdjie.github.io")
        .loadProject();
    // assertEquals(res.size(), 3);
    assertEquals(res.get(0).getName(), "home");
    assertEquals(res.get(1).getName(), "guides");
    assertEquals(res.get(2).getName(), "setup");
  }

}
