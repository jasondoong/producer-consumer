package com.client;

import com.jason.ConsumerElement;

/**
 * Created by jason on 2015/1/24.
 */
public class MyConsumer extends ConsumerElement<Message> {
  @Override
  public void run() {
    try{
      Message msg;
      //consuming messages until exit message is received
      while((msg = getFromBuffer()).getMsg() !="exit"){
        System.out.println("                        "+
          "["+Thread.currentThread().getId()+"]"+
          " takes "+msg.getMsg());
        Thread.sleep(1000);
      }
    }catch(InterruptedException e) {
      System.out.println("Consumer stops cause of interrupt");
    }
  }
}
