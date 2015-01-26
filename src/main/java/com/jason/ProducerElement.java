package com.jason;

public abstract class ProducerElement<T> extends Element<T> {

  protected void addToBuffer(T object) throws InterruptedException {
    this.queue.put(object);
  }

}
