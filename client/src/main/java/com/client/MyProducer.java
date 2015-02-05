package com.client;

import com.jason.ProducerElement;

/**
 * Created by jason on 2015/1/24.
 */
public class MyProducer extends ProducerElement<String> {

  String basicMessage;

  public MyProducer(String message){
    this.basicMessage = message;
  }

  public MyProducer(){
    this.basicMessage = "default";
  }

  @Override
  protected void produce() throws InterruptedException {
    for(int i=0; i<3; i++){
      String msg = "["+Thread.currentThread().getId()+"]"+":"+
        this.basicMessage+" "+i;
      addToBuffer(msg);
      System.out.println(" Puts "+msg);
    }
  }

  @Override
  protected void interruptHandler() {
    System.out.println("producer is interrupted");
  }
  
}
