package com.knoldus;

import java.util.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {
    public static void main(String[] args){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "com.knoldus.UserSerializer");


        KafkaProducer<String, User> kafkaProducer = new KafkaProducer(properties);
        try{
            User u1 = new User(1, "Steve", 24, "BTECH");
            User u2 = new User(2, "David", 23, "MCA");


            kafkaProducer.send(new ProducerRecord<String, User>("user", "SUP", u1)).get();
            kafkaProducer.send(new ProducerRecord<String, User>("user", "SUP", u2)).get();

            System.out.println("UserProducer Completed.");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            kafkaProducer.close();
        }
    }
}