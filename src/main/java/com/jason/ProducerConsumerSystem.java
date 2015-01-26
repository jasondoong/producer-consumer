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
  private ProducerConsumerSystem thisSystem;

  enum State{
    BeforeRunning, Running
  }

  public ProducerConsumerSystem(){

    setState(State.BeforeRunning);
    thisSystem = this;
  }

  public void start() throws InstantiationException, IllegalAccessException {

    startMainFlow();
    stopConsumerWhenProducerEnd();
  }

  private void startMainFlow()
    throws IllegalAccessException, InstantiationException {

    initialBuffer();
    setQueueToAllElements();
    startAllElements();
    setState(State.Running);
    System.out.println("Producer and Consumer has been started");
  }

  private void stopConsumerWhenProducerEnd() {
    new Thread(){
      public void run(){
        while(true) {
          if(thisSystem.isAllProcuderEnd()){
            tryToStopAllConsumer();
          }

          if(thisSystem.isAllConsumerEnd()){
            break;
          }
          threadSleep(500);
        }
      }
    }.start();
  }

  private void initialBuffer() {
    queue = new ArrayBlockingQueue<>(queueSize);
  }

  private void setQueueToAllElements() {
    for(ProducerElement p : this.producerList){
      p.setQueue(queue);
    }
    for(ConsumerElement c : this.consumerList){
      c.setQueue(queue);
    }
  }

  private void startAllElements() {
    addToThreadSetAndStartAllProducer();
    addToThreadSetAndStartAllConsumer();
  }

  private boolean isAllProcuderEnd() {
    return isAllThreadsEnd(this.producerThreads);
  }

  private boolean isAllConsumerEnd(){
    return isAllThreadsEnd(this.consumerThreads);
  }

  private void tryToStopAllConsumer(){
    for (Thread t : consumerThreads) {
      if (t.getState() == Thread.State.WAITING) {
        t.interrupt();
      }
    }
  }

  private Boolean isAllThreadsEnd(Collection<Thread> threads) {
    Boolean allEnd = true;
    for (Thread t : threads) {
      if (t.getState() != Thread.State.TERMINATED) {
        allEnd = false;
      }
    }
    return allEnd;
  }

  private synchronized void setState(State newState){
    this.state = newState;
  }

  private synchronized State getState(){
    return this.state;
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
    Thread t = new Thread(producerObj);
    this.producerThreads.add(t);
    t.start();
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

  //I don't like everytime I want threads to sleep , I have to do try catch
  // in that method, so I create this method.
  void threadSleep(int milliseconds){
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}


