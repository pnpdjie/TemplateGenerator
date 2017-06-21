package com.onap.template.controller;

import com.onap.template.Main;
import com.onap.template.jekyll.Constants;
import com.onap.template.jekyll.MenuGenerator;
import com.onap.template.jekyll.MenuLoader;
import com.onap.template.jekyll.MenuTypeGenerator;
import com.onap.template.jekyll.StringValidator;
import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;
import com.onap.template.ui.ProgressDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建导航模板控制器.
 * 
 * @author ywx474563 2017年6月16日
 */
public class CreateMenuTypeController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(CreateMenuTypeController.class);

  /**
   * 导航模板xml文件路径.
   */
  private static final String menuXmlPath = System.getProperty("user.dir") + "\\config\\Menus.xml";

  /**
   * 创建导航模板窗口.
   */
  @FXML
  private GridPane paneTemplate;

  /**
   * 简称输入框.
   */
  @FXML
  private TextField txtName;

  /**
   * 全称输入框.
   */
  @FXML
  private TextField txtDesc;

  /**
   * 上传模板按钮.
   */
  @FXML
  private Button btnUpload;

  /**
   * 创建按钮.
   */
  @FXML
  private Button btnCreate;

  /**
   * 模板文件路径容器.
   */
  @FXML
  private VBox vboxTemplate;

  /**
   * 上传的导航模板.
   */
  private List<String> templatePaths;

  /**
   * 上次选择模板路径.
   */
  private String lastFilePath;

  /**
   * 提示框.
   */
  private Alert tip;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Platform.runLater(() -> {
      // 提示框初始化.
      tip = new Alert(Alert.AlertType.INFORMATION);
      tip.setTitle("提示");
      tip.initOwner(mainStage);
      tip.setHeaderText(null);

      templatePaths = new ArrayList<String>();

      // 选择模板按钮绑定事件
      btnUpload.setOnAction(event -> {
        showFileDialog();
      });

      // 创建模板按钮绑定事件
      btnCreate.setOnAction(event -> {
        createMenuType();
      });
    });
  }

  /**
   * 浏览导航模板.
   */
  private void showFileDialog() {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("选择导航模板文件");
    chooser.getExtensionFilters().add(new ExtensionFilter("Markdown文件", "*.md"));
    if (StringUtils.isEmpty(lastFilePath)) {
      chooser.setInitialDirectory(new File(System.getProperty("user.home")));
    } else {
      chooser.setInitialDirectory(new File(lastFilePath));
    }
    List<File> list = chooser.showOpenMultipleDialog(mainStage);
    if (list != null && list.size() > 0) {
      lastFilePath = FilenameUtils.getFullPath(list.get(0).getAbsolutePath());
      addTemplateSection(list);
    }
  }

  /**
   * 增加模板路径显示.
   * 
   * @param list
   *          模板文件列表
   */
  private void addTemplateSection(List<File> list) {
    StringBuilder sb = new StringBuilder();
    for (File file : list) {
      String templatePath = file.getAbsolutePath();

      // 判断模板文件是否md类型
      if (!StringUtils.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()),
          Constants.JEKYLL_MD_EXTENSION.substring(1))) {

        sb.append("模板文件");
        sb.append(templatePath);
        sb.append("不是markdown文件\r\n");
        continue;
      }

      // 判断文件是否已选
      if (templatePaths.contains(templatePath)) {
        sb.append("模板文件");
        sb.append(templatePath);
        sb.append("已选，不能重复选择\r\n");
        continue;
      }

      templatePaths.add(templatePath);
      vboxTemplate.getChildren().add(new Label(templatePath));
    }
    if (sb.length() > 0) {
      tip.setContentText(sb.toString());
      tip.showAndWait();
      return;
    }
  }

  private void createMenuType() {
    final String name = txtName.getText().trim();
    final String desc = txtDesc.getText().trim();

    MenuTypeGenerator menuGenerator = new MenuTypeGenerator(name, desc, templatePaths) {

      @Override
      public void afterSucceeded() {
        // 重新初始化界面菜单
        Main.getInstance().rebuildMenuBar();
      }

      @Override
      public void afterFailed() {

      }
    };

    ProgressDialog.getInstance(mainStage).show().exec(menuGenerator);
  }

}
