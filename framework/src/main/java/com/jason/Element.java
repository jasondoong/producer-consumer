package com.jason;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jason on 2015/1/24.
 */
public abstract class Element<T> implements Runnable{
  protected BlockingQueue<T> queue;
  public void setQueue(BlockingQueue<T> q){
    this.queue=q;
  }
}
