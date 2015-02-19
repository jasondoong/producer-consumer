package com.jason;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jason on 2015/2/18.
 */
public class ObservableBufferImpl<E> implements ObservableBuffer<E> {

  private final BlockingQueue<E> queue;

  public ObservableBufferImpl(int bufferSize) {
    queue = new ArrayBlockingQueue<>(bufferSize);
  }

  @Override
  public int size() {
    return this.queue.size();
  }

  @Override
  public E take() throws InterruptedException {
    return this.queue.take();
  }

  @Override
  public void put(E item) throws InterruptedException {
    this.queue.put(item);
  }
}
