package com.jason;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerSystem <T>{

  private int queueSize;
  private Collection<Producer> producerList = new HashSet<Producer>();
  private Collection<Consumer> consumerList = new HashSet<Consumer>();
  private State state;
  private BlockingQueue<T> queue;

  public ProducerConsumerSystem(){
    setState(State.BeforeRunning);
  }

  public void run(){
    try {
      setState(State.Running);
      mainFlow();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
  }

  private synchronized void setState(State newState){
    this.state = newState;
  }

  private synchronized State getState(){
    return this.state;
  }

  private void mainFlow()
    throws IllegalAccessException, InstantiationException {
    queue = new ArrayBlockingQueue<>(queueSize);
    for(Producer p : this.producerList){
      p.setQueue(queue);
    }
    for(Consumer c : this.consumerList){
      c.setQueue(queue);
    }

    //starting producer to produce messages in queue
    for(Producer p : this.producerList) {
      new Thread(p).start();
    }
    //starting consumer to consume messages from queue
    for(Consumer c : this.consumerList) {
      new Thread(c).start();
    }

    System.out.println("Producer and Consumer has been started");
  }

  public void setBufferSize(int queueSize) {
    this.queueSize = queueSize;
  }

  public void addProducer(Producer producerObj){
    if(getState() == State.BeforeRunning) {
      this.producerList.add(producerObj);
    }else if(getState() == State.Running){
      setQueueAndStart(producerObj);
    }
  }

  private void setQueueAndStart(Producer producerObj) {
    producerObj.setQueue(queue);
    new Thread(producerObj).start();
  }

  public <P extends Producer> AddProducerTemp addProducer(
    Class<P> producerClass) {
    return new AddProducerTemp(producerClass, this);
  }

  public void addConsumer(Consumer consumerObj) {
    this.consumerList.add(consumerObj);
  }

  public <C extends Consumer> AddConsumerTemp addConsumer(
    Class<C> consumerClass) {
    return new AddConsumerTemp(consumerClass, this);
  }

  public void addConsumers(Collection<Consumer> consumers) {
    this.consumerList.addAll(consumers);
  }

  public void addProducers(Collection<Producer> producers) {
    this.producerList.addAll(producers);
  }

  enum State{
    BeforeRunning, Running
  }

}


