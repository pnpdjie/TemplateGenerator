package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.onap.template.JfxRunner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * MenuTypeGenerator单元测试.
 * 
 * @author ywx474563 2017年6月21日
 */
@RunWith(JfxRunner.class)
public class MenuTypeGeneratorTest {
  String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\_test_files\\";

  String menuXmlPath = System.getProperty("user.dir") + "\\_test_jekyll_project\\config\\Menus.xml";

  /**
   * 测试简称已存在.
   */
  @Test
  public void testNameExist() {
    List<String> list = new ArrayList<String>();
    String filePath = relativePath + "test2.md";
    list.add(filePath);
    MenuTypeGenerator generator = new MenuTypeGenerator("guides", "testnameexist", list,
        menuXmlPath) {

      @Override
      public void afterSucceeded(String msg) {
      }

      @Override
      public void afterFailed(String msg) {
      }

    };
    try {
      boolean res = generator.call();
      assertEquals(res, false);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "简称已存在，请重新输入");
    }
  }

  /**
   * 测试上传空文档列表.
   */
  @Test
  public void testFileList() {
    List<String> list = new ArrayList<String>();
    MenuTypeGenerator generator = new MenuTypeGenerator("testfilelist", "testFileList", list,
        menuXmlPath) {

      @Override
      public void afterSucceeded(String msg) {
      }

      @Override
      public void afterFailed(String msg) {
      }

    };
    try {
      boolean res = generator.call();
      assertEquals(res, false);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "请选择模板文件");
    }
  }

  /**
   * 测试非md格式文档.
   */
  @Test
  public void testFileExtensionOfCopyTemplateFile() {
    List<String> list = new ArrayList<String>();
    String filePath = relativePath + "test1.txt";
    list.add(filePath);
    MenuTypeGenerator generator = new MenuTypeGenerator("testfileextensionofcopytemplatefile",
        "testFileExtensionOfCopyTemplateFile", list, menuXmlPath) {

      @Override
      public void afterSucceeded(String msg) {
      }

      @Override
      public void afterFailed(String msg) {
      }

    };
    try {
      boolean res = generator.call();
      assertEquals(res, false);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "模板文件" + filePath + "不是markdown文件");
    }
  }

  /**
   * 测试简称已存在.
   */
  @Test
  public void testCreateMenuTemplateSuccess() {
    List<String> list = new ArrayList<String>();
    String filePath = relativePath + "test2.md";
    list.add(filePath);
    MenuTypeGenerator generator = new MenuTypeGenerator("testcreatemenutemplatesuccess",
        "testCreateMenuTemplateSuccess", list, menuXmlPath) {

      @Override
      public void afterSucceeded(String msg) {
      }

      @Override
      public void afterFailed(String msg) {
      }

    };
    try {
      boolean res = generator.call();
      assertEquals(res, true);

      //删除刚创建的导航模板，避免下次测试失败
      MenuLoader.removeMenuType(menuXmlPath, "testcreatemenutemplatesuccess");
    } catch (Exception e) {
      fail("创建导航模板失败");
    }
  }

}
