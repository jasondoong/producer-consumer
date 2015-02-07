package com.jason;

import com.jason.record.QueueSizeLoggerImpl;

public abstract class ProducerElement<T> extends Element<T> {

  protected void addToBuffer(T object) throws InterruptedException {
    this.queue.put(object);
  }

  @Override
  public void run() {
    try {
      open();
      while(hasNext()){

        T object = produce();
        addToBuffer(object);

        logQueuSize();
      }
      close();
    } catch (InterruptedException e) {
      interruptHandler();
    }
  }

  protected void open(){};

  protected abstract boolean hasNext();

  protected abstract T produce();

  protected void close(){}

  protected abstract void interruptHandler();

}
