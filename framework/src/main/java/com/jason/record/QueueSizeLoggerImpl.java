package com.jason.record;

import com.jason.ObservableBuffer;

/**
 * Created by jason on 2015/2/6.
 */

public class QueueSizeLoggerImpl implements QueueSizeLogger {

  private ObservableBuffer queue;
  private long startTime;
  private String log = "";

  public QueueSizeLoggerImpl(ObservableBuffer queue){
    this.queue = queue;
    startLogging();
  }

  //Todo: Don't append log here, store the log into some structure, and print the structure in printLog()
  @Override
  public void logQueuSize() {
    long interval = System.currentTimeMillis() - startTime;
    log = log + interval + "\t\t" + queue.size() + "\n";
  }

  @Override
  public void close(){
      printLog();
  }

  private void startLogging() {
    startTime = System.currentTimeMillis();
  }

  //Todo: make the printed output better
  private void printLog() {
    System.out.println("========================");
    System.out.println("interval(ms)\t\tqueue size");
    System.out.println("========================");
    System.out.println(log);
    return;
  }
}
