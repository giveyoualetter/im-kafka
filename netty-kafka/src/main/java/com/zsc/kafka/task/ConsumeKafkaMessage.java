package com.zsc.kafka.task;

import com.zsc.kafka.utils.KafkaUtils;
import kafka.common.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/24 19:15
 */
public class ConsumeKafkaMessage {
    //{"data":[{},{}]}
    public static String consume(KafkaConsumer consumer){
            String str = "{\"data\":[";
            List<String> list = new ArrayList<String>();
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                // print the offset,key and value for the consumer records.
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                consumer.commitSync();
                Map< TopicPartition, OffsetAndMetadata > map = new HashMap<>();
                consumer.commitSync(map);
                //KafkaUtils.getPartitionsForTopic(consumer,"2000");
                list.add(record.value());
            }
            if(list.size()!=0) {
                for (String s : list) {
                    str += (s + ",");
                }
                str = str.substring(0, str.length() - 1);
                str += "]}";
            }else{
                return "";//return "{\"data\":\"[]\"}";
            }
            return str;
    }
}
