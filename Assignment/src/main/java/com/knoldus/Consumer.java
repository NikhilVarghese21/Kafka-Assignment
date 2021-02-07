package com.knoldus;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.File;
import java.io.FileWriter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    
    public static void main(String[] args) {
        ConsumerListener c = new ConsumerListener();
        Thread thread = new Thread(c);
        thread.start();
    }
      public static void consumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "com.knoldus.UserDeserializer");
        properties.put("group.id", "test-group");

        KafkaConsumer<String, User> kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("user");
        kafkaConsumer.subscribe(topics);
        try{
            while(true){

                File file = new File("user.txt");
                FileWriter fr = new FileWriter(file, true);

                ConsumerRecords<String, User> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, User> record: records)
                {
                    System.out.println("User Id = " + record.value().getUserId() + " " +
                           "Name = " + record.value().getName() + " " +
                            "Age= " + record.value().getAge() + " " +
                            "Course = " + record.value().getCourse());

                    // writing value to  file
                    fr.write("User Id = " + record.value().getUserId() + " " +
                            "Name = " + record.value().getName() + " " +
                            "Age= " + record.value().getAge() + " " +
                            "Course = " + record.value().getCourse() + "\n");

                }
                fr.close();

            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            kafkaConsumer.close();
        }
    }
}

class ConsumerListener implements Runnable {
    
    
    @Override
    public void run() {
    Consumer.consumer();
    }
}