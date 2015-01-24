package com.client;

import com.jason.Producer;

/**
 * Created by jason on 2015/1/24.
 */
public class MyProducer extends Producer <Message>{

  String basicMessage;
  public MyProducer(String message){
    this.basicMessage = message;
  }

  public MyProducer(){
    this.basicMessage = "default";
  }
  @Override
  public void run() {
    //produce messages
    for(int i=0; i<10; i++){
      Message msg = new Message("["+Thread.currentThread().getId()+"]"+":"+
        this.basicMessage+" "+i);
      try {
        Thread.sleep(500);
        queue.put(msg);
        System.out.println(
          " Puts "+msg.getMsg());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    //adding exit message
    Message msg = new Message("exit");
    try {
      queue.put(msg);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
