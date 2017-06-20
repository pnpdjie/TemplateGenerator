package com.onap.template.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * CreateMenuTypeController单元测试.
 * 
 * @author ywx474563 2017年6月20日
 */
public class CreateMenuTypeControllerTest {
  String relativePath = System.getProperty("user.dir") + "\\_test_jekyll_project\\_test_files\\";

  /**
   * 测试非md格式文档.
   */
  @Test
  public void testFileExtensionOfCopyTemplateFile() {
    CreateMenuTypeController controller = new CreateMenuTypeController();
    List<File> list = new ArrayList<File>();
    String filePath = relativePath+"test1.txt";
    list.add(new File(filePath));
    try {
      controller.copyTemplateFile(list);
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "模板文件" + filePath + "不是markdown文件");
    }
  }

}
