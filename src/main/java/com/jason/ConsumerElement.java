package com.jason;

public abstract class ConsumerElement<T> extends Element<T> {

  protected T getFromBuffer() throws InterruptedException {
    return queue.take();
  }

}
