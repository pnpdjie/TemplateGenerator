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
public class SingleJfxApplication extends Application {
  /** 保证只有一个JavaFX线程运行. */
  private static final ReentrantLock LOCK = new ReentrantLock();

  /** 已开启标志. */
  private static AtomicBoolean started = new AtomicBoolean();

  /**
   * 启动JavaFx.
   * 
   * @throws InitializationError InitializationError
   * @throws InterruptedException InterruptedException
   */
  public static void startJavaFx() throws InitializationError {
    try {
      // 锁定或等待
      LOCK.lock();

      if (!started.get()) {
        // start the JavaFX application
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> jfxLaunchFuture = executor.submit(() -> SingleJfxApplication.launch());

        while (!started.get()) {
          try {
            jfxLaunchFuture.get(1, TimeUnit.MILLISECONDS);
          } catch (InterruptedException | TimeoutException e) {
            // continue waiting until success or error
          }
          Thread.yield();
        }
      }
    } catch (ExecutionException e) {
      throw new InitializationError(e);
    } finally {
      LOCK.unlock();
    }
  }

  /**
   * 启动.
   */
  protected static void launch() {
    Application.launch();
  }

  /**
   * An empty start method.
   *
   * @param stage
   *          The stage
   */
  @Override
  public void start(final Stage stage) {
    started.set(Boolean.TRUE);
  }
}
