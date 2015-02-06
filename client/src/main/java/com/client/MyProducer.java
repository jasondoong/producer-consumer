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


  int count;

  @Override
  protected void open(){
    count = 0;
  }

  @Override
  protected boolean hasNext(){
    return count < 10;
  }

  @Override
  protected String produce(){
    count++;
    String msg = this.basicMessage+" "+count;
    System.out.println(msg);
    return  msg;
  }

  protected void close(){
    System.out.println("MyProducer "+this.basicMessage+" closed");
  }

  @Override
  protected void interruptHandler() {
    System.out.println("producer is interrupted");
  }
  
}
