package com.client;

import com.jason.ConsumerElement;
import com.jason.ProducerElement;
import com.jason.ProducerConsumerSystem;

import java.util.Collection;
import java.util.HashSet;

public class Main {

    public static void main(String[] args)
      throws InstantiationException, IllegalAccessException{


        ProducerConsumerSystem system = new ProducerConsumerSystem();
        system.addProducer(MyProducer.class).instanceNum(3);
        system.addConsumer(MyConsumer.class).instanceNum(5);
        system.setBufferSize(20);
        system.start();

        //you can add new producer after system starts
        system.addProducer(new MyProducer("dynamic add"));


    }

    private static void addProducers(ProducerConsumerSystem system)
      throws InstantiationException, IllegalAccessException{
        //you can add producer one by one
        system.addProducer(new MyProducer("cool"));
        system.addProducer(new MyProducer("yes"));

        //or you can add a list of producer
        Collection<ProducerElement> producers = new HashSet<>();
        producers.add(new MyProducer("one"));
        producers.add(new MyProducer("two"));
        system.addProducers(producers);

        // or you can pass the producer class and the numbers of producer you want
        system.addProducer(MyProducer.class).instanceNum(3);
    }

    private static void addConsumers(ProducerConsumerSystem system)
      throws IllegalAccessException, InstantiationException {
        //you can add consumer one by one
        system.addConsumer(new MyConsumer());
        system.addConsumer(new MyConsumer());

        //or you can add a list of consumer objects
        Collection<ConsumerElement> consumers = new HashSet<>();
        consumers.add(new MyConsumer());
        consumers.add(new MyConsumer());
        system.addConsumers(consumers);

        // or you can pass the consumer class and the numbers of consumer you want
        system.addConsumer(MyConsumer.class).instanceNum(5);
    }

}
