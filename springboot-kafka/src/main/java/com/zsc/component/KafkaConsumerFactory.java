package com.zsc.component;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/18 22:40
 */
public class KafkaConsumerFactory {
    public static KafkaConsumer<String,String> getConsumer(String brokerAddress, String groupID, String topicName) {
        //Kafka consumer configuration settings
        Properties props = new Properties();

        props.put("bootstrap.servers", brokerAddress);//"localhost:9092"
        props.put("group.id", groupID);//"test"
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
    }

}
