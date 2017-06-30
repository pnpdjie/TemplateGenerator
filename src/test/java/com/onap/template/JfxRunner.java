package com.onap.template;

import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;

import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * 保证JavaFX运行，需要用到JavaFX的测试可以正常运行.
 * 
 * @author ywx474563 2017年6月22日
 */
public class JfxRunner extends BlockJUnit4ClassRunner {

  private static final String ERROR_ON_TIMEOUT = "@TestInJfxThread 超时.";

  /**
   * 构造JavaFxJUnit4ClassRunner.
   * 
   * @param clazz
   *          和Runner一起运行的类
   * @throws InitializationError
   *           抛出的异常
   */
  public JfxRunner(final Class<?> clazz) throws InitializationError {
    super(clazz);

    JavaFxApp.startJfxApplication();
  }

  /**
   * 运行测试方法.
   */
  @Override
  protected void runChild(final FrameworkMethod method, final RunNotifier notifier) {
    // 创建latch，只有在super runChild()实现后才移除
    final CountDownLatch latch = new CountDownLatch(1);

    // 检查方法是否需要在JavaFX线程运行.
    TestInJfxThread performMethodInFxThread = method.getAnnotation(TestInJfxThread.class);
    if (performMethodInFxThread != null) {
      Test annotation = method.getAnnotation(Test.class);
      long timeout = annotation.timeout();

      if (timeout > 0) {
        System.err.println(ERROR_ON_TIMEOUT);
        throw new UnsupportedOperationException(ERROR_ON_TIMEOUT);
      }

      Platform.runLater(() -> {
        JfxRunner.super.runChild(method, notifier);
        latch.countDown();
      });
    } else {
      JfxRunner.super.runChild(method, notifier);
      latch.countDown();
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
