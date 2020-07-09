package com.zsc.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import java.util.Collections;
import java.util.Properties;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/18 8:45
 */
@Configuration
@ComponentScan("com.zsc.component")
public class KafkaConfig {

    @Value("${kafka.topicName}")
    private String topicName = "topic1";

//    @Bean
//    KafkaProducerFactory createKafkaProducerFactory(){
//        return new KafkaProducerFactory();
//    }
//
//    @Bean
//    KafkaConsumerFactory createKafkaConsumerFactory(){
//        return new KafkaConsumerFactory();
//    }

    @Bean
    Producer createProducer(){
        // create instance for properties to access producer configs
        Properties props = new Properties();
        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer
                <String, String>(props);
        return producer;
//      for(int i = 0; i < 10; i++)
//            producer.send(new ProducerRecord<String, String>(topicName,
//            Integer.toString(i), Integer.toString(i)));
//               System.out.println(“Message sent successfully");
//            producer.close();
    }
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    KafkaConsumer<String,String> createConsumer(){
        //Kafka consumer configuration settings
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        //Thread.currentThread().setContextClassLoader(null);
        KafkaConsumer<String, String> consumer = new KafkaConsumer
                <String, String>(props);

        //Kafka Consumer subscribes list of topics here.
        //topicName需要是一个set?
        consumer.subscribe(Collections.singleton(topicName));
        return consumer;
        //print the topic name
//        System.out.println("Subscribed to topic "+ topicName);
//        int i = 0;
//
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(100);
//            for (ConsumerRecord<String, String> record : records)
//
//                // print the offset,key and value for the consumer records.
//                System.out.printf("offset = %d, key = %s, value = %s\n",
//                        record.offset(), record.key(), record.value());
    }

}
