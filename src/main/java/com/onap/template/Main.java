package com.onap.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.controller.LauncherController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

  /**
   * JavaFX应用启动.
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      mainStage = primaryStage;

      // 构建初始化界面
      outerRoot = new BorderPane();
     // AnchorPane ap_launcher = new AnchorPane((Node) FXMLLoader.load(Main.class.getResource("Launcher.fxml")));
      
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Launcher.fxml"));
      Parent root = fxmlLoader.load();    
      LauncherController controller = (LauncherController)fxmlLoader.getController();   //获取Controller的实例对象
      controller.setMainStage(mainStage);
      
      outerRoot.setCenter(root);

      // 显示界面
      Scene scene = new Scene(outerRoot, 600, 200);
      scene.getStylesheets().add(appCssUrl);
      primaryStage.setScene(scene);
      primaryStage.setTitle("模板生成工具启动");
      primaryStage.setResizable(false);
      primaryStage.show();
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
}
