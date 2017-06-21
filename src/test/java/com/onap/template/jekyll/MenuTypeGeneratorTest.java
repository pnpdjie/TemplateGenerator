package com.onap.template.jekyll;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * MenuTypeGenerator单元测试.
 * 
 * @author ywx474563 2017年6月21日
 */
public class MenuTypeGeneratorTest {
  String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\_test_files\\";

  /**
   * 测试非md格式文档.
   */
  @Test
  public void testFileExtensionOfCopyTemplateFile() {
    List<String> list = new ArrayList<String>();
    String filePath = relativePath + "test1.txt";
    list.add(filePath);
    MenuTypeGenerator generator = new MenuTypeGenerator("a", "b", list) {

      @Override
      public void afterSucceeded() {
        // TODO Auto-generated method stub

      }

      @Override
      public void afterFailed() {
        // TODO Auto-generated method stub

      }

    };
    try {
      Thread th = new Thread(generator);
      th.start();
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "模板文件" + filePath + "不是markdown文件");
    }
  }

}
