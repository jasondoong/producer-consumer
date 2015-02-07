package com.jason.record;

/**
 * Created by jason on 2015/2/7.
 */
public class NullQueueSizeLogger implements QueueSizeLogger {

  @Override
  public void logQueuSize() {
    return;
  }

  @Override
  public void close() {
    return;
  }
}
