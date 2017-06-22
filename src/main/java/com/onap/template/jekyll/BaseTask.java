package com.onap.template.jekyll;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.concurrent.Task;

import org.apache.commons.io.FileUtils;

/**
 * 任务基类.
 * 
 * @author ywx474563 2017年6月21日
 */
public abstract class BaseTask<T> extends Task<T> {

  /**
   * 日志.
   */
  private StringBuilder logBuilder = new StringBuilder();

  /**
   * 实时修改日志.
   */
  @Override
  protected void updateMessage(String message) {
    logBuilder.append(message + "\r\n");
    super.updateMessage(logBuilder.toString());
  }

  /**
   * 执行成功.
   */
  @Override
  protected void succeeded() {
    super.succeeded();
    updateMessage("执行成功");

    createLogFile();

    updateTitle("执行成功");

    afterSucceeded(logBuilder.toString());
  }

  /**
   * 执行失败.
   */
  @Override
  protected void failed() {
    super.failed();
    updateMessage("执行失败");

    createLogFile();

    updateTitle("执行失败");

    afterFailed(logBuilder.toString());
  }

  /**
   * 生成日志文件.
   */
  private void createLogFile() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String logPath = System.getProperty("user.dir") + "\\logs\\" + getLogName()
        + formatter.format(new Date()) + ".log";
    try {
      FileUtils.write(new File(logPath), logBuilder.toString(), Constants.ENCODING);
      updateMessage("日志文件路径：" + logPath);
    } catch (IOException e) {
      updateMessage("日志文件生成失败");
    }
  }

  /**
   * 日志文件名.
   */
  public abstract String getLogName();

  /**
   * 执行成功后.
   */
  public abstract void afterSucceeded(String msg);

  /**
   * 执行失败后.
   */
  public abstract void afterFailed(String msg);
}
