package com.jason;

/**
 * Created by jason on 2015/1/24.
 */
public  class ConsumerBuilder<C extends ConsumerElement>{
  private Class<C> consumerClass;
  private ProducerConsumerSystem system;
  public ConsumerBuilder(Class<C> consumerClass,
    ProducerConsumerSystem system) {
    this.consumerClass = consumerClass;
    this.system = system;
  }

  public void instanceNum(int instanceNum)
    throws IllegalAccessException, InstantiationException {

    for(int i=0; i<instanceNum; i++) {
      system.startConsumer(this.consumerClass.newInstance());
    }
  }
}
