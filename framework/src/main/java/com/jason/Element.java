package com.jason;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jason on 2015/1/24.
 */
public abstract class Element<T> implements Runnable{
  protected BlockingQueue<T> queue;
  public ProducerConsumerSystem hostedSystem;

  public Thread initAndStart(ProducerConsumerSystem system, BlockingQueue queue){
    init(system, queue);
    return start();
  }

  private void init(ProducerConsumerSystem system, BlockingQueue queue) {
    this.setHostedSystem(system);
    this.setQueue(queue);
  }

  private Thread start() {
    Thread t = new Thread(this);
    t.start();
    return t;
  }

  protected void logQueuSize(){
    hostedSystem.logQueueSize();
  }

  private void setHostedSystem(ProducerConsumerSystem system){
    this.hostedSystem = system;
  }

  private void setQueue(BlockingQueue<T> q){
    this.queue=q;
  }

}
