package com.jason;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

public class ProducerConsumerSystem <T>{

  private Collection<Thread> producerThreads = new CopyOnWriteArraySet<>();
  private Collection<Thread> consumerThreads = new CopyOnWriteArraySet<>();

  private BlockingQueue<T> queue;
  private ProducerConsumerSystem thisSystem;

  public ProducerConsumerSystem(int bufferSize){
    thisSystem = this;
    initialBuffer(bufferSize);
    waitingFirstProducer();
  }

  private void initialBuffer(int bufferSize) {
    queue = new ArrayBlockingQueue<>(bufferSize);
  }

  private void waitingFirstProducer() {
    new Thread(){
      public void run(){
        while(true) {
          if(thisSystem.producerThreads.size()>0){
            stopConsumerWhenProducerEnd();
            break;
          }
          threadSleep(500);
        }
      }
    }.start();
  }

  private void stopConsumerWhenProducerEnd() {
    new Thread(){
      public void run(){
        while(true) {
          if(consumersShouldStop()){
            tryToStopAllConsumer();
          }

          if(thisSystem.isAllConsumerEnd()){
            break;
          }
          threadSleep(500);
        }
        systemClose();
      }
    }.start();
  }
  private void systemClose(){
    QueueSizeLogger.close();
  }

  private boolean consumersShouldStop() {
    return isAllProcuderEnd()&& isBufferEmpty();
  }

  private boolean isBufferEmpty(){
    return this.queue.size()==0;
  }
  private boolean isAllProcuderEnd() {
    return isAllThreadsEnd(this.producerThreads);
  }

  private boolean isAllConsumerEnd(){
    return isAllThreadsEnd(this.consumerThreads);
  }

  private void tryToStopAllConsumer(){
    System.out.println("Try to stop all consumers");
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

  public void startProducers(Collection<ProducerElement> producers) {
    for(ProducerElement p : producers){
      startProducer(p);
    }
  }

  public void startProducer(ProducerElement producerObj){
    producerObj.setQueue(queue);
    Thread t = createProducerThread(producerObj);
    t.start();
  }

  private Thread createProducerThread(ProducerElement producerObj) {
    Thread t = new Thread(producerObj);
    this.producerThreads.add(t);
    return t;
  }

  public <P extends ProducerElement> StartProducerTemp startProducer(
    Class<P> producerClass) {
    return new StartProducerTemp(producerClass, this);
  }


  public void startConsumers(Collection<ConsumerElement> consumers) {
    for(ConsumerElement c : consumers){
      startConsumer(c);
    }
  }

  public void startConsumer(ConsumerElement consumerObj) {
    consumerObj.setQueue(queue);
    Thread t = createConsumerThread(consumerObj);
    t.start();
  }

  private Thread createConsumerThread(ConsumerElement consumerObj) {
    Thread t = new Thread(consumerObj);
    this.consumerThreads.add(t);
    return t;
  }

  public <C extends ConsumerElement> startConsumerTemp startConsumer(
    Class<C> consumerClass) {
    return new startConsumerTemp(consumerClass, this);
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

  public void enableQueueLogger() {
    QueueSizeLogger.enable(queue);
  }
}


