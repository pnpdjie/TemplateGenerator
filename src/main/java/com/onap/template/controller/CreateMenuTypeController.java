package com.onap.template.controller;

import com.onap.template.Main;
import com.onap.template.jekyll.MenuLoader;
import com.onap.template.jekyll.StringValidator;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
   * 创建按钮.
   */
  @FXML
  private Button btnCreate;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Platform.runLater(() -> {
      btnCreate.setOnAction(event -> {
        createMenuType();
      });
    });
  }

  private void createMenuType() {
    final String name = txtName.getText().trim();
    final String desc = txtDesc.getText().trim();

    // 提示框
    Alert tip = new Alert(Alert.AlertType.INFORMATION);
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

    boolean res = MenuLoader.addMenuType(menuXmlPath, new MetaMenu(name, desc));
    logger.warn(desc + "(" + name + ")" + (res ? "创建成功" : "创建失败"));
    tip.setContentText(res ? "创建成功" : "创建失败");
    tip.showAndWait();

    Main.getInstance().rebuildMenuBar();
  }

}
