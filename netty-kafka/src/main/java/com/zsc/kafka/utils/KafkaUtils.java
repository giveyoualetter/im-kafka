package com.zsc.kafka.utils;

import java.util.*;
import java.util.Map.Entry;

import kafka.admin.RackAwareMode;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.security.JaasUtils;
import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;
import scala.collection.JavaConverters;
import kafka.utils.ZKStringSerializer$;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.kafka.clients.consumer.KafkaConsumer;
/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/24 21:13
 */
public class KafkaUtils {

    // 获取某个Topic的所有分区以及分区最新的Offset
    public static void getPartitionsForTopic(KafkaConsumer consumer, final String topicName) {
        Collection<PartitionInfo> partitionInfos = consumer.partitionsFor(topicName);
        System.out.println("Get the partition info as below:");
        partitionInfos.forEach(str -> {
            System.out.print("Partition Info:");
            System.out.println(str);
//            tp.add(new TopicPartition(topicName,str.partition()));
//            consumer.assign(tp);
//            consumer.seekToEnd(tp);
            System.out.println("Partition " + str.partition() + " 's latest offset is '" + consumer.position(new TopicPartition(topicName, str.partition())));
            OffsetAndMetadata om = consumer.committed(new TopicPartition(topicName, str.partition()));
            System.out.println("committed:"+om);//OffsetAndMetadata{offset=8, leaderEpoch=null, metadata=''}
        });
    }
    //检查是否有名为topicName的主题
    public static boolean hasTopic(String topicName){
        ZkUtils zkUtils = ZkUtils.apply("127.0.0.1:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Map<String, Properties> topics = JavaConverters.mapAsJavaMapConverter(AdminUtils.fetchAllTopicConfigs(zkUtils)).asJava();
        for (Entry<String, Properties> entry : topics.entrySet()) {
            String key = entry.getKey();
            if(key.equals(topicName))return true;
            Object value = entry.getValue();
        }
        zkUtils.close();
        return false;
    }
    //创建主题topicName
    public static void createTopic(String topicName){
        ZkClient zkClient = null;
        ZkUtils zkUtils = null;
        try {
            String zookeeperHosts = "127.0.0.1:2181"; // If multiple zookeeper then -> String zookeeperHosts = "192.168.20.1:2181,192.168.20.2:2181";
            int sessionTimeOutInMs = 15 * 1000; // 15 secs
            int connectionTimeOutInMs = 10 * 1000; // 10 secs

            zkClient = new ZkClient(zookeeperHosts, sessionTimeOutInMs, connectionTimeOutInMs, ZKStringSerializer$.MODULE$);
            zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperHosts), false);

            int noOfPartitions = 1;
            int noOfReplication = 1;
            Properties topicConfiguration = new Properties();

            AdminUtils.createTopic(zkUtils, topicName, noOfPartitions, noOfReplication, topicConfiguration,new RackAwareMode.Safe$());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zkClient != null) {
                zkClient.close();
            }
        }
    }

}
