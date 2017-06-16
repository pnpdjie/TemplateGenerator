package com.onap.template.ui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onap.template.Main;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * 进度条弹出框.
 * 
 * @author ywx474563 2017年6月16日
 */
public class ProgressPane {

  private static final Logger logger = LoggerFactory.getLogger(ProgressPane.class);
  
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

  private static ProgressPane instance;

  public static ProgressPane getInstance(Window owner) {
    if (instance == null) {
      instance = new ProgressPane(owner);
    }
    return instance;
  }

  private ProgressPane(Window owner) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProgressDialog.fxml"));
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
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public ProgressPane show() {
    stage.show();
    return instance;
  }

  public void close() {
    stage.close();
  }

  public void exec(Task task) {
    stage.titleProperty().bind(task.titleProperty());
    progressExec.progressProperty().bind(task.progressProperty());
    areaLog.textProperty().bind(task.messageProperty());
    new Thread(task).start();
  }
//
//  Task<Integer> task1 = new Task<Integer>() {
//    private StringBuilder logBuilder = new StringBuilder();
//
//    @Override
//    protected Integer call() throws Exception {
//      for (int i = 0; i < 100; i++) {
//        Thread.sleep(50);
//        updateProgress(i + 1, 100);
//        updateMessage("Loading..." + (i + 1) + "%");
//      }
//      updateMessage("执行结束");
//      return null;
//    }
//
//    @Override
//    protected void running() {
//      super.running();
//    }
//
//    @Override
//    protected void succeeded() {
//      super.succeeded();
//      updateMessage("执行成功");
//    }
//
//    @Override
//    protected void failed() {
//      super.failed();
//      updateMessage("执行失败");
//    }
//
//    @Override
//    protected void updateProgress(long workDone, long max) {
//      super.updateProgress(workDone, max);
//    }
//
//    @Override
//    protected void updateMessage(String message) {
//      logBuilder.append(message + "\r\n");
//      super.updateMessage(logBuilder.toString());
//    }
//
//    @Override
//    protected void updateTitle(String title) {
//      super.updateTitle(title);
//    }
//
//  };
}
