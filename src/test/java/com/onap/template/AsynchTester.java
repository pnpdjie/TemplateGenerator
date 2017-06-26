package com.onap.template;

/**
 * 线程执行.
 * 
 * @author ywx474563 2017年6月23日
 */
public class AsynchTester {
  private Thread thread;
  private volatile AssertionError exc;

  /**
   * 异步测试，在主线程抛出测试异常.
   * 
   * @param runnable
   *          任务
   */
  public AsynchTester(final Runnable runnable) {
    thread = new Thread(new Runnable() {
      public void run() {
        try {
          runnable.run();
        } catch (AssertionError e) {
          exc = e;
        }
      }
    });
  }

  public void start() {
    thread.start();
  }

  /**
   * 运行线程，直到完成.
   * 
   * @throws InterruptedException
   *           异常
   */
  public void test() throws InterruptedException {
    thread.join();
    if (exc != null) {
      throw exc;
    }
  }
}
