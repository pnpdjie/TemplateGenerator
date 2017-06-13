package com.onap.template.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动界面.
 * 
 * @author ywx474563
 *
 */
public class Launcher {

  private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

  private AnchorPane pane;

  /**
   * 读取fxml文件初始化界面.
   */
  public Launcher() {
    try {
      this.pane = new AnchorPane((Node) FXMLLoader.load(Launcher.class.getResource("Launcher.fxml")));
    } catch (IOException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }

  public AnchorPane getPane() {
    return pane;
  }
}
