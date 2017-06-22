package com.onap.template;

import com.onap.template.controller.CreateMenuTypeController;
import com.onap.template.controller.LauncherController;
import com.onap.template.jekyll.Launcher;
import com.onap.template.jekyll.MenuGenerator;
import com.onap.template.jekyll.MenuLoader;
import com.onap.template.model.JekyllMenu;
import com.onap.template.model.Menus;
import com.onap.template.model.MetaMenu;
import com.onap.template.model.Project;
import com.onap.template.ui.CreatedMenus;
import com.onap.template.ui.ProgressDialog;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private TabPane contentTabs;
  private LauncherController launcherController;

  /**
   * 选中的Jekyll项目路径.
   */
  private String jekyllProjectPath;

  private RadioMenuItem selectedJekyllProject;

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
      buildLauncherUi(primaryStage);
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * 主程序入口.
   */
  public static void main(String[] args) {

    PropertyConfigurator.configure("config\\log4j.properties");
    launch(args);
  }

  /**
   * 构建启动界面.
   * 
   * @param primaryStage
   *          菜单数据列表
   */
  public void buildLauncherUi(Stage primaryStage) {
    try {
      mainStage = primaryStage;
      root = new BorderPane();

      // 构建初始化界面
      outerRoot = new BorderPane();

      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Launcher.fxml"));
      root.setCenter(fxmlLoader.load());
      launcherController = (LauncherController) fxmlLoader.getController(); // 获取Controller的实例对象
      launcherController.setMainStage(mainStage);

      outerRoot.setCenter(root);

      // 显示界面
      Scene scene = new Scene(outerRoot, 600, 200);
      scene.getStylesheets().add(appCssUrl);
      primaryStage.setScene(scene);
      primaryStage.setTitle("模板生成工具启动");
      primaryStage.setResizable(false);
      primaryStage.getIcons()
          .add(new Image(this.getClass().getResource("images/favicon.png").toString()));
      primaryStage.show();
      instance = this;

    } catch (IOException e) {
      logger.error(e.getMessage());
      // e.printStackTrace();
    }
  }

  /**
   * 构建主界面.
   * 
   * @param primaryStage
   *          界面容器
   * @param listMenu
   *          Jekyll项目导航数据
   * @param choosedPath
   *          Jekyll项目路径
   */
  public void buildMainUi(Stage primaryStage, List<com.onap.template.model.JekyllMenu> listMenu,
      String choosedPath) {
    jekyllProjectPath = choosedPath;

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
    contentTabs = new TabPane();
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
    primaryStage.setTitle("模板生成工具，项目路径：" + jekyllProjectPath);
    primaryStage.getIcons()
        .add(new Image(this.getClass().getResource("images/favicon.png").toString()));
    primaryStage.show();
    instance = this;

  }

  /**
   * 重新读取菜单.
   */
  public void rebuildMenuBar() {
    MenuBar menuBar = buildMenuBar();
    outerRoot.setTop(menuBar);
  }

  /**
   * 重启主界面数据.
   * 
   * @param listMenu
   *          Jekyll项目导航数据
   * @param choosedPath
   *          Jekyll项目路径
   */
  private void rebuildMainUi(List<com.onap.template.model.JekyllMenu> listMenu,
      String choosedPath) {
    jekyllProjectPath = choosedPath;

    // 构建初始化界面
    outerRoot = new BorderPane();
    // 设置界面顶部菜单
    outerRoot.setTop(buildMenuBar());

    root = new BorderPane();

    // 构建已创建的Jekyll项目菜单
    CreatedMenus menus = new CreatedMenus(listMenu);

    // 构建界面内容
    contentTabs = new TabPane();
    Tab tab = new Tab("已创建导航");
    tab.setContent(menus);
    tab.setClosable(false);
    contentTabs.getTabs().add(tab);
    root.setCenter(contentTabs);
    outerRoot.setCenter(root);

    mainStage.getScene().setRoot(outerRoot);
    mainStage.setTitle("模板生成工具，项目路径：" + jekyllProjectPath);
    // mainStage.getIcons().add(new
    // Image(this.getClass().getResource("images/favicon.png").toString()));
    // mainStage.show();
    instance = this;
  }

  /**
   * 构建菜单栏.
   * 
   * @return 菜单栏
   */
  private MenuBar buildMenuBar() {
    MenuBar menuBar = new MenuBar();
    menuBar.setUseSystemMenuBar(true);

    // 初始化创建菜单
    Menu createMenu = new Menu("创建");
    Menu menus = new Menu("导航");

    // 从Menus.xml文件加载菜单
    String menuXmlPath = System.getProperty("user.dir") + "\\config\\Menus.xml";
    Menus loadedMenus = MenuLoader.loadMenus(menuXmlPath);
    if (loadedMenus == null) {
      Alert tip = new Alert(Alert.AlertType.INFORMATION);
      tip.setTitle("提示");
      tip.initOwner(mainStage);
      tip.setHeaderText(null);
      tip.setContentText("菜单xml文件读取失败");
      tip.showAndWait();
      return null;
    }
    for (MetaMenu m : loadedMenus.getMetaMenus()) {
      menus.getItems().add(buildMenuItem(m, loadedMenus));
    }

    MenuItem type = new MenuItem("导航模板");
    createMenu.getItems().addAll(menus, type);
    type.setOnAction(event -> createJekyllMenuType());

    // 初始化切换菜单
    Menu switchMenu = new Menu("切换");
    ToggleGroup tg = new ToggleGroup();

    // 读取Jekyll项目路径
    List<Project> projectList = launcherController.getProjects();
    for (Project project : projectList) {
      switchMenu.getItems().add(buildSwitchMenuItem(project, tg));
    }

    menuBar.getMenus().addAll(createMenu, switchMenu);
    return menuBar;
  }

  /**
   * 构建创建导航菜单.
   * 
   * @param name
   *          导航名称
   * @return 菜单
   */
  private MenuItem buildMenuItem(MetaMenu metaMenu, Menus loadedMenus) {
    MenuItem item = new MenuItem(metaMenu.getDesc() + "(" + metaMenu.getName() + ")");
    for (JekyllMenu jekyllMenu : Launcher.getInstance().getListMenu()) {
      if (StringUtils.equalsIgnoreCase(jekyllMenu.getName(), metaMenu.getName())) {
        item.setDisable(true);
        break;
      }
    }
    item.setOnAction(event -> createJekyllMenu(metaMenu, loadedMenus));
    return item;
  }

  /**
   * 构建切换项目菜单.
   * 
   * @param project
   *          Jekyll项目
   * @return 菜单
   */
  private RadioMenuItem buildSwitchMenuItem(Project project, ToggleGroup tg) {
    RadioMenuItem rmItem = new RadioMenuItem(project.getPath());
    if (StringUtils.equalsIgnoreCase(project.getPath(), jekyllProjectPath)) {
      rmItem.setSelected(true);
      selectedJekyllProject = rmItem;
    }
    rmItem.setOnAction(event -> switchJekyllProject(event, project));
    rmItem.setToggleGroup(tg);
    return rmItem;
  }

  /**
   * 创建Jekyll项目导航菜单.
   * 
   * @param name
   *          项目路径
   */
  private void createJekyllMenu(MetaMenu metaMenu, Menus loadedMenus) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("确认");
    alert.initOwner(mainStage);
    alert.setHeaderText(null);
    alert.setContentText("确认创建导航" + metaMenu.getDesc() + "(" + metaMenu.getName() + ")" + "?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      // 创建Jekyll菜单
      MenuGenerator menuGenerator = new MenuGenerator(metaMenu, loadedMenus,
          System.getProperty("user.dir") + "\\config\\MdTemplate.md") {

        @Override
        public void afterSucceeded(String msg) {
          // 重新初始化界面数据
          List<JekyllMenu> listMenu = launcherController.getJekyllMenu(jekyllProjectPath);
          rebuildMainUi(listMenu, jekyllProjectPath);
        }

        @Override
        public void afterFailed(String msg) {

        }
      };

      ProgressDialog.getInstance(mainStage).show().exec(menuGenerator);
    }

  }

  /**
   * 创建Jekyll项目导航菜单类型.
   * 
   * @param name
   *          项目路径
   */
  private void createJekyllMenuType() {
    try {
      Tab tab = new Tab("创建导航模板");

      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ui/CreateMenuType.fxml"));
      ScrollPane pane = new ScrollPane(fxmlLoader.load());
      CreateMenuTypeController createMenuTypeController = (CreateMenuTypeController) fxmlLoader
          .getController(); // 获取Controller的实例对象
      createMenuTypeController.setMainStage(mainStage);

      // Node node =
      // FXMLLoader.load(Main.class.getResource("ui/CreateMenuType.fxml"));
      tab.setContent(pane);
      contentTabs.getTabs().add(tab);
      SingleSelectionModel<Tab> selectionModel = contentTabs.getSelectionModel();
      selectionModel.select(tab);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 切换Jekyll项目.
   * 
   * @param event
   *          事件
   * @param project
   *          Jekyll项目路径信息
   */
  private boolean switchJekyllProject(ActionEvent event, Project project) {
    RadioMenuItem menu = (RadioMenuItem) event.getSource();

    if (StringUtils.equalsIgnoreCase(project.getPath(), jekyllProjectPath)) {
      // menu.setSelected(false);
      // selectedJekyllProject.setSelected(true);
      return false;
    }

    try {
      List<JekyllMenu> listMenu = launcherController.getJekyllMenu(project.getPath());
      rebuildMainUi(listMenu, project.getPath());
      // menu.setSelected(true);
      selectedJekyllProject = menu;
      return true;
    } catch (RuntimeException e) {
      logger.error(e.getLocalizedMessage());
      menu.setSelected(false);
      selectedJekyllProject.setSelected(true);

      Alert tip = new Alert(Alert.AlertType.INFORMATION);
      tip.setTitle("提示");
      tip.initOwner(mainStage);
      tip.setHeaderText(null);
      tip.setContentText(e.getMessage());
      tip.showAndWait();
      return false;
    }
  }

  public void closeTab(Tab tab) {
    contentTabs.getTabs().remove(tab);
  }

}
