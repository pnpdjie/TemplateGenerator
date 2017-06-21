package com.onap.template.controller;

import com.onap.template.jekyll.Constants;
import com.onap.template.model.JekyllMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主界面控制器.
 * 
 * @author ywx474563 2017年6月14日
 */
public class CreatedMenuController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(LauncherController.class);

  /**
   * Jekyll项目导航简称.
   */
  @FXML
  private Label lblName;

  /**
   * Jekyll项目导航全称.
   */
  @FXML
  private Label lblDesc;

  /**
   * Jekyll项目导航数据文件路径.
   */
  @FXML
  private Label lblLeftTreePath;

  /**
   * Jekyll项目导航数据文件内容.
   */
  @FXML
  private TextArea areaLeftTreeFile;

  /**
   * Jekyll项目导航主页文件路径.
   */
  @FXML
  private Label lblIndexPath;

  /**
   * Jekyll项目导航主页文件内容.
   */
  @FXML
  private TextArea areaIndexFile;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

  }

  /**
   * 主界面控件赋值.
   * @param menu Jekyll项目导航数据
   */
  public void initData(JekyllMenu menu) {
    lblName.setText(menu.getName());
    lblDesc.setText(menu.getDesc());
    lblLeftTreePath.setText(menu.getLeftTreePath());
    lblIndexPath.setText(menu.getIndexPath());

    try {
      File leftTreeFile = menu.getLeftTreeFile();
      if (leftTreeFile != null && leftTreeFile.exists()) {
        areaLeftTreeFile.setText(FileUtils.readFileToString(leftTreeFile, Constants.ENCODING));
      }

      File indexFile = menu.getIndexFile();
      if (indexFile != null && indexFile.exists()) {
        areaIndexFile.setText(FileUtils.readFileToString(indexFile, Constants.ENCODING));
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

}
