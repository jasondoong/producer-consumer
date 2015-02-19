package com.jason;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jason on 2015/2/18.
 */
public class ObservableBufferImpl<E> extends Observable implements ObservableBuffer<E> {

  private final BlockingQueue<E> buffer;

  public ObservableBufferImpl(int bufferSize) {
    buffer = new ArrayBlockingQueue<>(bufferSize);
  }

  @Override
  public int size() {
    return this.buffer.size();
  }

  @Override
  public E take() throws InterruptedException {
    E item = this.buffer.take();
    notifyBufferSize();
    return item;
  }

  @Override
  public void put(E item) throws InterruptedException {
    this.buffer.put(item);
    notifyBufferSize();
  }

  private void notifyBufferSize() {
    String message = "Buffer size is "+ buffer.size();
    setChanged();
    notifyObservers(message);
  }

}
