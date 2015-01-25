package com.jason;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerSystem <T>{

  private int queueSize;
  private Collection<ProducerElement> producerList = new HashSet<ProducerElement>();
  private Collection<ConsumerElement> consumerList = new HashSet<ConsumerElement>();
  private Collection<Thread> producerThreads = new HashSet<>();
  private Collection<Thread> consumerThreads = new HashSet<>();
  private State state;
  private BlockingQueue<T> queue;

  public ProducerConsumerSystem(){
    setState(State.BeforeRunning);
  }

  public void run(){
    try {
      setState(State.Running);
      mainFlow();
      //Todo: set state to State.AfterRunnig when all tasks end
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
    setQueueToAllElements();
    startAllElements();
    System.out.println("Producer and Consumer has been started");
  }

  private void startAllElements() {
    addToThreadSetAndStartAllProducer();
    addToThreadSetAndStartAllConsumer();
  }

  private void addToThreadSetAndStartAllConsumer() {
    //starting consumer to consume messages from queue
    for(ConsumerElement c : this.consumerList) {
      Thread t = new Thread(c);
      consumerThreads.add(t);
      t.start();
    }
  }

  private void addToThreadSetAndStartAllProducer() {
    //starting producer to produce messages in queue
    for(ProducerElement p : this.producerList) {
      Thread t = new Thread(p);
      producerThreads.add(t);
      t.start();
    }
  }

  private void setQueueToAllElements() {
    for(ProducerElement p : this.producerList){
      p.setQueue(queue);
    }
    for(ConsumerElement c : this.consumerList){
      c.setQueue(queue);
    }
  }

  public void setBufferSize(int queueSize) {
    this.queueSize = queueSize;
  }

  public void addProducer(ProducerElement producerObj){
    if(getState() == State.BeforeRunning) {
      this.producerList.add(producerObj);
    }else if(getState() == State.Running){
      setQueueAndStart(producerObj);
    }
  }

  private void setQueueAndStart(ProducerElement producerObj) {
    producerObj.setQueue(queue);
    new Thread(producerObj).start();
  }

  public <P extends ProducerElement> AddProducerTemp addProducer(
    Class<P> producerClass) {
    return new AddProducerTemp(producerClass, this);
  }

  public void addConsumer(ConsumerElement consumerObj) {
    this.consumerList.add(consumerObj);
  }

  public <C extends ConsumerElement> AddConsumerTemp addConsumer(
    Class<C> consumerClass) {
    return new AddConsumerTemp(consumerClass, this);
  }

  public void addConsumers(Collection<ConsumerElement> consumers) {
    this.consumerList.addAll(consumers);
  }

  public void addProducers(Collection<ProducerElement> producers) {
    this.producerList.addAll(producers);
  }

  enum State{
    BeforeRunning, Running
  }

}


