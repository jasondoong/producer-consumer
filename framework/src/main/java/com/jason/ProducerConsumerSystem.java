package com.jason;

import com.jason.record.NullQueueSizeLogger;
import com.jason.record.QueueSizeLogger;
import com.jason.record.QueueSizeLoggerImpl;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

public class ProducerConsumerSystem <T>{

  private final int bufferSize;
  private Collection<Thread> producerThreads = new CopyOnWriteArraySet<>();
  private Collection<Thread> consumerThreads = new CopyOnWriteArraySet<>();

  private BlockingQueue<T> queue;
  private ProducerConsumerSystem thisSystem;
  private QueueSizeLogger queueSizeLogger = new NullQueueSizeLogger();

  public ProducerConsumerSystem(int bufferSize){
    thisSystem = this;
    this.bufferSize = bufferSize;
    initialProcedure();
  }


  //first way to start producer/consumer
  public void startProducer(ProducerElement producerObj){
    Thread t = producerObj.initAndStart(this, queue);
    this.producerThreads.add(t);
  }

  public void startConsumer(ConsumerElement consumerObj) {
    Thread t = consumerObj.initAndStart(this, queue);
    this.consumerThreads.add(t);
  }


  //second way to start producer/consumer
  public <P extends ProducerElement> ProducerBuilder startProducer(
    Class<P> producerClass) {
    return new ProducerBuilder(producerClass, this);
  }

  public <C extends ConsumerElement> ConsumerBuilder startConsumer(
    Class<C> consumerClass) {
    return new ConsumerBuilder(consumerClass, this);
  }


  //third way to start producer/consumer
  public void startProducers(Collection<ProducerElement> producers) {
    for(ProducerElement p : producers){
      startProducer(p);
    }
  }

  public void startConsumers(Collection<ConsumerElement> consumers) {
    for(ConsumerElement c : consumers){
      startConsumer(c);
    }
  }


  private void initialProcedure() {
    initialBuffer(this.bufferSize);
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
    queueSizeLogger.close();
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


  public void enableQueueLogger() {
    queueSizeLogger = new QueueSizeLoggerImpl(queue);
  }

  public void logQueueSize() {
    queueSizeLogger.logQueuSize();
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


