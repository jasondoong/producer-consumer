package com.jason;

public abstract class ConsumerElement<T> extends Element<T> {

  protected T getFromBuffer() throws InterruptedException {
    return queue.take();
  }

  @Override
  public void run() {
    try{
      //consuming messages until exit message is received
      while(true){

        T item = getFromBuffer();
        consume(item);

      }
    }catch(InterruptedException e) {
      interruptHandler();
    }
  }

  protected abstract void consume(T item) throws InterruptedException;

  protected abstract void interruptHandler();

}
