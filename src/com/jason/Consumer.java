package com.jason;

import java.util.concurrent.BlockingQueue;

public abstract class Consumer implements Runnable{

  protected BlockingQueue<Message> queue;

  public void setQueue(BlockingQueue<Message> queue) {
    this.queue = queue;
  }
}
