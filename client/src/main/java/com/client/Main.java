package com.client;

import com.jason.ConsumerElement;
import com.jason.ProducerElement;
import com.jason.ProducerConsumerSystem;

import java.util.Collection;
import java.util.HashSet;

public class Main {

    public static void main(String[] args)
      throws InstantiationException, IllegalAccessException{

      example1();
    }

    private static void example1() throws InstantiationException, IllegalAccessException {
      int bufferSize = 20;
      ProducerConsumerSystem system = new ProducerConsumerSystem(bufferSize);
      system.startProducer(MyProducer.class).instanceNum(2);
      system.startConsumer(MyConsumer.class).instanceNum(1);


      //you can add new producer after system starts
      system.startProducer(new MyProducer("dynamic"));

      //you can add new consumer too
      system.startConsumer(new MyConsumer());
    }

    private static void addProducers(ProducerConsumerSystem system)
      throws InstantiationException, IllegalAccessException{
        //you can add producer one by one
        system.startProducer(new MyProducer("cool"));
        system.startProducer(new MyProducer("yes"));

        //or you can add a list of producer
        Collection<ProducerElement> producers = new HashSet<>();
        producers.add(new MyProducer("one"));
        producers.add(new MyProducer("two"));
        system.startProducers(producers);

        // or you can pass the producer class and the numbers of producer you want
        system.startProducer(MyProducer.class).instanceNum(3);
    }

    private static void addConsumers(ProducerConsumerSystem system)
      throws IllegalAccessException, InstantiationException {
        //you can add consumer one by one
        system.startConsumer(new MyConsumer());
        system.startConsumer(new MyConsumer());

        //or you can add a list of consumer objects
        Collection<ConsumerElement> consumers = new HashSet<>();
        consumers.add(new MyConsumer());
        consumers.add(new MyConsumer());
        system.startConsumers(consumers);

        // or you can pass the consumer class and the numbers of consumer you want
        system.startConsumer(MyConsumer.class).instanceNum(5);
    }

}
