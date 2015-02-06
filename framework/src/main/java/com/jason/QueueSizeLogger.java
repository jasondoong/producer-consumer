package com.jason;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jason on 2015/2/6.
 */

//Todo: What if 2 ProducerConsumerSystem start and ask to enable QueueSizeLogger?

public class QueueSizeLogger {

  private static boolean queueLoggerEnabled = false;
  private static BlockingQueue queue;
  private static long startTime;
  private static String log = "";

  public static <T> void enable(BlockingQueue<T> queue) {
    queueLoggerEnabled = true;
    setQueue(queue);
    startLogging();
  }

  public static boolean isQueueLoggerEnabled() {
    return queueLoggerEnabled;
  }

  private static void setQueue(BlockingQueue q) {
    queue = q;
  }

  private static void startLogging() {
    startTime = System.currentTimeMillis();
  }

  //Todo: Don't append log here, store the log into some structure, and print the structure in printLog()
  public static void logQueuSize() {
    long interval = System.currentTimeMillis() - startTime;
    log = log + interval + "\t\t" + queue.size() + "\n";
  }

  public static void close(){
    if(queueLoggerEnabled)
      printLog();
  }

  //Todo: make the printed output better
  private static void printLog() {
    System.out.println("========================");
    System.out.println("interval(ms)\t\tqueue size");
    System.out.println("========================");
    System.out.println(log);
    return;
  }
}
