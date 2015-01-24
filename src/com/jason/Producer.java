package com.jason;

import java.util.concurrent.BlockingQueue;

public abstract class Producer <T> implements Runnable {

  protected BlockingQueue<T> queue;
  public void setQueue(BlockingQueue<T> q){
    this.queue=q;
  }

}
