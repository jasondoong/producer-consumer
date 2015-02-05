package com.client;

import com.jason.ConsumerElement;

/**
 * Created by jason on 2015/1/24.
 */
public class MyConsumer extends ConsumerElement<String> {

  @Override
  protected void consume(String msg) throws InterruptedException {
    System.out.println("                        "+
      "["+Thread.currentThread().getId()+"]"+
      " takes "+msg);
    Thread.sleep(1000);
  }

  @Override
  protected void interruptHandler() {
    System.out.println("Consumer stops cause of interrupt");
  }
}
