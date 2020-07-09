package com.zsc.service;

import com.alibaba.fastjson.JSON;
import com.zsc.component.KafkaProducerFactory;
import com.zsc.component.KafkaSender;
import com.zsc.dto.ClientMsg;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/25 9:32
 */
public class SendMsgToKafka {
    public static void sendMsg(ClientMsg msg){
        long peerUserId = msg.getPeerUserId();
        String jsonStr= JSON.toJSONString(msg);
        System.out.println(jsonStr);
        KafkaSender.sendMessage(KafkaProducerFactory.getKafkaProducer("127.0.0.1:9092"),new ProducerRecord(String.valueOf(peerUserId),jsonStr));
    }
}
