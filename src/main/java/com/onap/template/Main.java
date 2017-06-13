package com.onap.template;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.ui.Launcher;

import javafx.application.Application;
import javafx.scene.Scene;
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

      // build Menu Bar
      outerRoot = new BorderPane();
      Launcher launcher = new Launcher();
      outerRoot.setCenter(launcher.getPane());

      // show UI
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
