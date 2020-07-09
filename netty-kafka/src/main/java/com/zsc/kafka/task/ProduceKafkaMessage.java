package com.zsc.kafka.task;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/24 20:05
 */
public class ProduceKafkaMessage {
    public static void sendMessage(Producer producer, ProducerRecord producerRecord){
        producer.send(producerRecord);
    }
}
