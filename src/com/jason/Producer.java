package com.jason;

import java.util.concurrent.BlockingQueue;

public abstract class Producer implements Runnable {

  protected BlockingQueue<Message> queue;
  public void setQueue(BlockingQueue<Message> q){
    this.queue=q;
  }

}
