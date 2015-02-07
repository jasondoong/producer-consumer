package com.jason;

/**
 * Created by jason on 2015/1/24.
 */
public class ProducerBuilder<P extends ProducerElement>{
  private Class<P> producerClass;
  private ProducerConsumerSystem system;
  public ProducerBuilder(Class<P> producerClass,
    ProducerConsumerSystem system) {
    this.producerClass = producerClass;
    this.system = system;
  }

  public void instanceNum(int instanceNum)
    throws IllegalAccessException, InstantiationException{

    for(int i=0; i<instanceNum; i++) {
      system.startProducer(this.producerClass.newInstance());
    }
  }
}
