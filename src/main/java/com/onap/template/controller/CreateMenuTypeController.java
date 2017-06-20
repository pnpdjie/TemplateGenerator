package com.onap.template.controller;

import com.onap.template.Main;
import com.onap.template.jekyll.Constants;
import com.onap.template.jekyll.MenuLoader;
import com.onap.template.jekyll.StringValidator;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.MetaMenuTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
   * 提示框.
   */
  private Alert tip;

  private int rowIndex = 7;

  private List<MetaMenuTemplate> templates;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Platform.runLater(() -> {
      tip = new Alert(Alert.AlertType.INFORMATION);
      templates = new ArrayList<MetaMenuTemplate>();
      btnUpload.setOnAction(event -> {
        showFileDialog();
      });
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
    chooser.setInitialDirectory(new File(System.getProperty("user.home")));
    List<File> list = chooser.showOpenMultipleDialog(mainStage);
    if (list != null) {
      try {
        copyTemplateFile(list);
      } catch (RuntimeException e) {
        logger.error(e.getMessage());
        tip.setContentText(e.getMessage());
        tip.showAndWait();
      }
    }
  }

  /**
   * 复制模板文件到config目录.
   * 
   * @param list
   *          文件列表
   */
  public void copyTemplateFile(List<File> list) {
    for (File file : list) {
      String filePath = file.getAbsolutePath();

      // 判断模板文件是否md类型
      if (!StringUtils.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()),
          Constants.JEKYLL_MD_EXTENSION.substring(1))) {
        throw new RuntimeException("模板文件" + filePath + "不是markdown文件");
      }

      // 判断模板文件是否已存在，不能重复写入
      boolean contain = false;
      for (MetaMenuTemplate template : templates) {
        if (StringUtils.equalsIgnoreCase(template.getUploadPath(), filePath)) {
          contain = true;
          continue;
        }
      }

      if (contain) {
        throw new RuntimeException("模板文件" + filePath + "已经写入");
      }

      String savePath = "config\\" + UUID.randomUUID() + Constants.JEKYLL_MD_EXTENSION;
      try {
        String fileContent = FileUtils.readFileToString(file, Constants.ENCODING);
        FileUtils.write(new File(System.getProperty("user.dir") + "\\" + savePath), fileContent,
            Constants.ENCODING);
        templates.add(new MetaMenuTemplate(savePath, filePath));

        TextArea tArea = new TextArea(fileContent);
        tArea.setPrefColumnCount(10);
        tArea.setPrefRowCount(5);
        newSection("模板：", 10, new Label("源文件：" + filePath),
            new Label("生成文件：" + System.getProperty("user.dir") + "\\" + savePath), tArea);
      } catch (IOException e) {
        throw new RuntimeException("模板文件" + filePath + "写入出错");
      }
    }
  }

  /**
   * 模板复制后显示对应文件内容.
   * 
   * @param name
   *          描述
   * @param spacing
   *          间距
   * @param children
   *          控件
   */
  private void newSection(String name, int spacing, Node... children) {
    Label sectionLabel = new Label(name);
    sectionLabel.getStyleClass().add("section-label");
    sectionLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    VBox box = new VBox(spacing);
    box.getChildren().addAll(children);
    // paneTemplate.setConstraints(sectionLabel, 0, rowIndex, 1, 1, HPos.CENTER,
    // VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
    // paneTemplate.setConstraints(box, 1, rowIndex++);
    // paneTemplate.getChildren().addAll(sectionLabel, box);
    paneTemplate.add(sectionLabel, 0, rowIndex);
    paneTemplate.add(box, 1, rowIndex++);
  }

  private void createMenuType() {
    final String name = txtName.getText().trim();
    final String desc = txtDesc.getText().trim();

    tip.setTitle("提示");
    tip.initOwner(mainStage);
    tip.setHeaderText(null);

    // 判断name是否包含空格
    if (StringUtils.isEmpty(name)) {
      tip.setContentText("请输入简称");
      tip.showAndWait();
      return;
    }

    // 判断name是否包含空格
    if (StringUtils.isEmpty(desc)) {
      tip.setContentText("请输入全称");
      tip.showAndWait();
      return;
    }

    // 判断name是否包含空格
    if (StringUtils.containsWhitespace(name)) {
      tip.setContentText("简称有空格，请重新输入");
      tip.showAndWait();
      return;
    }

    // 判断是否包含特殊字符
    if (StringValidator.validateSpecialChar(name)) {
      tip.setContentText("简称含特殊字符，请重新输入");
      tip.showAndWait();
      return;
    }

    if (StringValidator.validateSpecialChar(desc)) {
      tip.setContentText("全称含特殊字符，请重新输入");
      tip.showAndWait();
      return;
    }

    // 判断name是否只含小写字母
    if (!StringValidator.validateLowercase(name)) {
      tip.setContentText("简称只能包含小写字母，请重新输入");
      tip.showAndWait();
      return;
    }

    // 判断desc是否只含大小写字母和空格
    if (!StringValidator.validateLowerUpperWhitespace(desc)) {
      tip.setContentText("全称只能包含大小写字母和空格，请重新输入");
      tip.showAndWait();
      return;
    }

    // 判断简称和全称是否已存在
    Menus loadedMenus = MenuLoader.loadMenus(menuXmlPath);
    for (MetaMenu metaMenu : loadedMenus.getMetaMenus()) {
      if (StringUtils.equalsIgnoreCase(name, metaMenu.getName())) {
        tip.setContentText("简称已存在，请重新输入");
        tip.showAndWait();
        return;
      }
    }

    // 判断是否选择模板文件
    if (templates.size() == 0) {
      tip.setContentText("请选择模板文件");
      tip.showAndWait();
      return;
    }

    boolean res = MenuLoader.addMenuType(menuXmlPath, new MetaMenu(name, desc, templates));
    logger.warn(desc + "(" + name + ")" + (res ? "创建成功" : "创建失败"));
    tip.setContentText(res ? "创建成功" : "创建失败");
    tip.showAndWait();

    Main.getInstance().rebuildMenuBar();
  }

}
