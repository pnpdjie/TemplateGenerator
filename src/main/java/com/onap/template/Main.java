package com.onap.template;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.controller.LauncherController;
import com.onap.template.jekyll.MenuLoader;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.controller.CreatedMenuController;
import com.onap.template.ui.CreatedMenus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 主界面.
 * 
 * @author ywx474563
 *
 */
public class Main extends Application {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private static final String appCssUrl = Main.class.getResource("App.css").toExternalForm();
  private Stage mainStage;
  private BorderPane outerRoot;
  private BorderPane root;

  private static Main instance;

  public static Main getInstance() {
    return instance;
  }

  /**
   * JavaFX应用启动.
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      buildLauncherUI(primaryStage);
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * 主程序入口.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * 构建启动界面
   * 
   * @param primaryStage
   * @param listMenu
   *          菜单数据列表
   */
  public void buildLauncherUI(Stage primaryStage) {
    try {
      mainStage = primaryStage;
      root = new BorderPane();

      // 构建初始化界面
      outerRoot = new BorderPane();

      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Launcher1.fxml"));
      root.setCenter(fxmlLoader.load());
      LauncherController controller = (LauncherController) fxmlLoader.getController(); // 获取Controller的实例对象
      controller.setMainStage(mainStage);

      outerRoot.setCenter(root);

      // 显示界面
      Scene scene = new Scene(outerRoot, 600, 200);
      scene.getStylesheets().add(appCssUrl);
      primaryStage.setScene(scene);
      primaryStage.setTitle("模板生成工具启动");
      primaryStage.setResizable(false);
      primaryStage.show();
      instance = this;
    } catch (IOException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * 构建主界面
   * 
   * @param primaryStage
   */
  public void buildMainUI(Stage primaryStage, List<com.onap.template.model.JekyllMenu> listMenu) {
    mainStage.close();
    root = null;

    mainStage = primaryStage;

    // 构建初始化界面
    outerRoot = new BorderPane();
    // 设置界面顶部菜单
    outerRoot.setTop(buildMenuBar());

    root = new BorderPane();

    // 构建已创建的Jekyll项目菜单
    CreatedMenus menus = new CreatedMenus(listMenu);

      // 构建界面内容
      TabPane contentTabs = new TabPane();
      Tab tab = new Tab("已创建导航");
      tab.setContent(menus);
      tab.setClosable(false);
      contentTabs.getTabs().add(tab);
      root.setCenter(contentTabs);
      outerRoot.setCenter(root);
    
    // 显示界面
    Scene scene = new Scene(outerRoot, 800, 600);
    scene.getStylesheets().add(appCssUrl);
    primaryStage.setScene(scene);
    primaryStage.setTitle("模板生成工具");
    primaryStage.show();
    instance = this;

  }

  /**
   * 构建菜单栏
   * 
   * @return
   */
  private MenuBar buildMenuBar() {
    MenuBar menuBar = new MenuBar();
    menuBar.setUseSystemMenuBar(true);
    
    //初始化创建菜单
    Menu createMenu = new Menu("创建");
    Menu menus = new Menu("导航");
    
    //从Menus.xml文件加载菜单
    Menus loadedMenus = MenuLoader.loadFromXml(Main.class.getResource("data/Menus.xml").getPath());
    for (MetaMenu m : loadedMenus.getMetaMenus()) {
      menus.getItems().add(buildMenuItem(m.getName()));
    }
    
    MenuItem type = new MenuItem("导航类型");
    createMenu.getItems().addAll(menus, type);
    
    //初始化切换菜单
    Menu switchMenu = new Menu("切换");
    ToggleGroup tg = new ToggleGroup();
    switchMenu.getItems().addAll(buildProjectItem("c:\\", tg), buildProjectItem("d:\\", tg),
        buildProjectItem("e:\\", tg));
    
    menuBar.getMenus().addAll(createMenu,switchMenu);
    return menuBar;
  }

  /**
   * 构建创建导航菜单.
   * 
   * @param name
   *          导航名称
   * @return 菜单
   */
  private MenuItem buildMenuItem(String name) {
    MenuItem item = new MenuItem(name);
    item.setOnAction(event -> createJekyllMenu(name));
    return item;
  }

  /**
   * 创建Jekyll项目导航菜单.
   * 
   * @param name
   *          项目路径
   */
  private void createJekyllMenu(String name) {

  }

  /**
   * 构建切换Jekyll项目菜单.
   * 
   * @param name
   *          项目路径
   * @param tg
   *          菜单组
   * @return 菜单
   */
  private RadioMenuItem buildProjectItem(String name, ToggleGroup tg) {
    RadioMenuItem rmItem = new RadioMenuItem(name);
    rmItem.setOnAction(event -> switchProject(name));
    rmItem.setToggleGroup(tg);
    return rmItem;
  }

  /**
   * 切换Jekyll项目.
   * 
   * @param name
   *          项目路径
   */
  private void switchProject(String name) {

  }

}
