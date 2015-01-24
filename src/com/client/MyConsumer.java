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
      while((msg = queue.take()).getMsg() !="exit"){
        System.out.println("                        "+
          "["+Thread.currentThread().getId()+"]"+
          " takes "+msg.getMsg());
        Thread.sleep(2000);
      }
    }catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
}
