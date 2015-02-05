package com.jason;

public abstract class ProducerElement<T> extends Element<T> {

  protected void addToBuffer(T object) throws InterruptedException {
    this.queue.put(object);
  }

  @Override
  public void run() {
    try {
      produce();
    } catch (InterruptedException e) {
      interruptHandler();
    }
  }

  protected abstract void interruptHandler();

  protected abstract void produce() throws InterruptedException;

}
