package com.jason;

import java.util.Observer;

/**
 * Created by jason on 2015/2/19.
 */
public interface ObservableBuffer<E> {

  int size();

  E take() throws InterruptedException;

  void put(E item) throws InterruptedException;

  public void addObserver(Observer observer);
}
