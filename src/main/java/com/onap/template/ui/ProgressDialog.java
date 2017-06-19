package com.onap.template.ui;

import com.onap.template.Main;

import java.io.IOException;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进度条弹出框.
 * 
 * @author ywx474563 2017年6月16日
 */
public class ProgressDialog {

  private static final Logger logger = LoggerFactory.getLogger(ProgressDialog.class);

  /**
   * 父窗口.
   */
  private Window owner;

  /**
   * 弹出框.
   */
  private Stage stage;

  /**
   * 进度条.
   */
  private ProgressBar progressExec;

  /**
   * 日志.
   */
  private TextArea areaLog;

  /**
   * 单例.
   */
  private static ProgressDialog instance;

  /**
   * 获取单例.
   * 
   * @param owner
   *          父窗口
   * @return 单例
   */
  public static ProgressDialog getInstance(Window owner) {
    if (instance == null) {
      instance = new ProgressDialog(owner);
    }
    return instance;
  }

  /**
   * 构造函数.
   * 
   * @param owner
   *          父窗口
   */
  private ProgressDialog(Window owner) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(
          ProgressDialog.class.getResource("ProgressDialog.fxml"));
      Parent root = fxmlLoader.load();

      progressExec = (ProgressBar) root.lookup("#progressExec");
      areaLog = (TextArea) root.lookup("#areaLog");

      stage = new Stage();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.initModality(Modality.WINDOW_MODAL);
      // 设置父窗口
      stage.initOwner(owner);
      stage.setTitle("执行中...");
      stage.initStyle(StageStyle.UNIFIED);
      stage.setResizable(false);
      stage.getIcons().add(new Image(Main.class.getResource("images/favicon.png").toString()));
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 显示弹出框.
   * 
   * @return 进度条弹出框
   */
  public ProgressDialog show() {
    stage.show();
    return instance;
  }

  /**
   * 关闭弹出框.
   */
  public void close() {
    stage.close();
  }

  /**
   * 执行任务.
   * 
   * @param task
   *          异步任务
   */
  public void exec(Task task) {
    stage.titleProperty().bind(task.titleProperty());
    progressExec.progressProperty().bind(task.progressProperty());
    areaLog.textProperty().bind(task.messageProperty());
    new Thread(task).start();
  }

}
