package com.jason;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jason on 2015/1/24.
 */
public abstract class Element<T> implements Runnable{
  protected BlockingQueue<T> queue;
  public ProducerConsumerSystem hostedSystem;

  public void setQueue(BlockingQueue<T> q){
    this.queue=q;
  }

  public void setHostedSystem(ProducerConsumerSystem system){
    this.hostedSystem = system;
  }

  protected void logQueuSize(){
    hostedSystem.logQueueSize();
  }
}
