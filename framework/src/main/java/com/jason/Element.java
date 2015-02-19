package com.jason;

/**
 * Created by jason on 2015/1/24.
 */
public abstract class Element<T> implements Runnable{
  protected ObservableBuffer<T> queue;
  public ProducerConsumerSystem hostedSystem;

  public Thread initAndStart(ProducerConsumerSystem system, ObservableBuffer queue){
    init(system, queue);
    return start();
  }

  private void init(ProducerConsumerSystem system, ObservableBuffer queue) {
    this.setHostedSystem(system);
    this.setQueue(queue);
  }

  private Thread start() {
    Thread t = new Thread(this);
    t.start();
    return t;
  }

  private void setHostedSystem(ProducerConsumerSystem system){
    this.hostedSystem = system;
  }

  private void setQueue(ObservableBuffer<T> q){
    this.queue=q;
  }

}
