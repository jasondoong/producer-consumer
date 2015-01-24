package com.jason;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerSystem <T>{

  private int queueSize;
  private Collection<Producer> producerList = new HashSet<Producer>();
  private Collection<Consumer> consumerList = new HashSet<Consumer>();

  public void run(){
    try {
      mainFlow();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
  }

  private void mainFlow()
    throws IllegalAccessException, InstantiationException {
    BlockingQueue<T> queue =
      new ArrayBlockingQueue<>(queueSize);

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

  public void addProducer(Producer producerObj) {
    this.producerList.add(producerObj);
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
}
