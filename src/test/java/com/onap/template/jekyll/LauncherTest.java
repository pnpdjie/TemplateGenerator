/**
 * 
 */
package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.onap.template.model.JekyllMenu;

/**
 * com.onap.template.jekyll.Launcher单元测试.
 * 
 * @author ywx474563 2017年6月15日
 */
public class LauncherTest {

  private static Launcher launcher;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    launcher = new Launcher(System.getProperty("user.dir")+"\\_test_jekyll_project\\pnpdjie.github.io");
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }
//
//  @Test
//  public void testHasConfigFile() {
//    boolean res = launcher.hasConfigFile();
//    assertEquals(res, true);
//  }
//
//  @Test
//  public void testHasDataDir() {
//    boolean res = launcher.hasDataDir();
//    assertEquals(res, true);
//  }
//
//  @Test
//  public void testHasMdDir() {
//    boolean res = launcher.hasMdDir();
//    assertEquals(res, true);
//  }

  @Test
  public void testLoadProject() { 
    
    List<JekyllMenu> res = launcher.loadProject();
//    assertEquals(res.size(), 3);
    assertEquals(res.get(0).getName(), "home");
    assertEquals(res.get(1).getName(), "guides");
    assertEquals(res.get(2).getName(), "setup");
  }

}
