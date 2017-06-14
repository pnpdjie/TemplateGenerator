package com.onap.template.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.Main;
import com.onap.template.jekyll.Launcher;
import com.onap.template.model.JekyllMenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 初始界面控制器.
 * 
 * @author ywx474563
 *
 */
public class LauncherController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(LauncherController.class);

  /**
   * 加载Jekyll项目业务处理
   */
  private Launcher launcher;

  /**
   * Jekyll项目路径选择框
   */
  @FXML
  private ComboBox<String> cbPath;

  /**
   * 浏览Jekyll项目按钮
   */
  @FXML
  private Button btnSelectFile;

  /**
   * 设置默认项目复选框
   */
  @FXML
  private CheckBox chkDefault;

  /**
   * 确定按钮
   */
  @FXML
  private Button btnOk;

  /**
   * 取消按钮
   */
  @FXML
  private Button btnCancel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Platform.runLater(() -> {
      btnSelectFile.setOnAction(event -> {
        showFileDialog();
      });
      btnOk.setOnAction(event -> {
        loadProject();
      });
      btnCancel.setOnAction(event -> {
        if (mainStage != null) {
          mainStage.close();
        }
      });
    });
  }

  /**
   * 浏览Jekyll项目
   */
  private void showFileDialog() {
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("选择Jekyll项目");
    String cwd = System.getProperty("user.dir");
    File file = new File(cwd);
    chooser.setInitialDirectory(file);
    File chosenDir = chooser.showDialog(null);
    if (chosenDir != null) {
      String choosedPath = chosenDir.getAbsolutePath();
      cbPath.setValue(choosedPath);
    }
  }

  /**
   * 加载Jekyll项目
   */
  private void loadProject() {
    String choosedPath = cbPath.getValue();

    Alert tip = new Alert(Alert.AlertType.INFORMATION);
    tip.setTitle("提示");
    tip.initOwner(mainStage);

    if (StringUtils.isEmpty(choosedPath)) {
      tip.setContentText("请选择项目路径");
      tip.showAndWait();
      return;
    }

    launcher = new Launcher(choosedPath);

    if (!launcher.hasConfigFile()) {
      tip.setContentText("选中目录不包含_config.yml文件，请选择正确的Jekyll项目目录");
      tip.showAndWait();
      return;
    }

    if (!launcher.hasDataDir()) {
      tip.setContentText("选中目录不包含_data文件夹，请选择正确的Jekyll项目目录");
      tip.showAndWait();
      return;
    }

    if (!launcher.hasMdDir()) {
      tip.setContentText("选中目录不包含docs文件夹，请选择正确的Jekyll项目目录");
      tip.showAndWait();
      return;
    }

    List<JekyllMenu> listMenu = launcher.loadProject();
    Main.getInstance().buildMainUI(new Stage(), listMenu);
  }
}
