package com.jason;

import java.util.concurrent.BlockingQueue;

public abstract class Consumer <T> implements Runnable{

  protected BlockingQueue<T> queue;

  public void setQueue(BlockingQueue<T> queue) {
    this.queue = queue;
  }
}
