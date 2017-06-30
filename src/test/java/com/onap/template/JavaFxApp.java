package com.onap.template;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Application;
import javafx.stage.Stage;

import org.junit.runners.model.InitializationError;

/**
 * 开启JavaFX的应用.
 * 
 * @author ywx474563 2017年6月22日
 */
public class JavaFxApp extends Application {

  /** 已开启标志. */
  private static AtomicBoolean started_jfx = new AtomicBoolean();
  
  /** 保证只有一个JavaFX线程运行. */
  private static final ReentrantLock LOCK_JFX = new ReentrantLock();

  /**
   * 空启动.
   *
   * @param stage 界面
   */
  @Override
  public void start(final Stage stage) {
    started_jfx.set(Boolean.TRUE);
  }

  /**
   * 启动.
   */
  protected static void launch() {
    Application.launch();
  }

  /**
   * 启动JavaFx.
   * 
   * @throws InitializationError InitializationError
   * @throws InterruptedException InterruptedException
   */
  public static void startJfxApplication() {
    try {
      // 锁定或等待
      LOCK_JFX.lock();

      if (!started_jfx.get()) {
        // 开启JFX
        final ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
        Future<?> jfxFuture = singleExecutor.submit(() -> JavaFxApp.launch());

        while (!started_jfx.get()) {
          try {
            jfxFuture.get(1, TimeUnit.MILLISECONDS);
          } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
          }
          Thread.yield();
        }
      }
    } catch (ExecutionException e) {
      try {
        throw new InitializationError(e);
      } catch (InitializationError e1) {
        e1.printStackTrace();
      }
    } finally {
      LOCK_JFX.unlock();
    }
  }
}
