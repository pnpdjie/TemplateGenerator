package com.onap.template.ui;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.Main;
import com.onap.template.controller.CreatedMenuController;
import com.onap.template.controller.LauncherController;
import com.onap.template.model.JekyllMenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * 已创建的菜单数据界面
 * 
 * @author ywx474563 2017年6月14日
 */
public class CreatedMenus extends BorderPane {

  private static final Logger logger = LoggerFactory.getLogger(CreatedMenus.class);

  public CreatedMenus(List<JekyllMenu> listMenu) {
    try {
      TabPane contentTabs = new TabPane();
      for (JekyllMenu menu : listMenu) {
        FXMLLoader fxmlLoader = new FXMLLoader(CreatedMenus.class.getResource("CreatedMenu.fxml"));
        Parent root = fxmlLoader.load();
        CreatedMenuController controller = (CreatedMenuController) fxmlLoader.getController(); // 获取Controller的实例对象
        controller.initData(menu);
        
        Tab tab = new Tab(menu.getName());
        tab.setContent(new ScrollPane(root));
        tab.setClosable(false);
        contentTabs.getTabs().add(tab);
      }
      setCenter(contentTabs);
    } catch (IOException ex) {
      logger.error(ex.getMessage());
    }
  }
}
