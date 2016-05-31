package com.edsoft;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.edsoft.esper.handler.iot.DataEventHandler;
import com.edsoft.iot.Data;
import com.edsoft.kafka.DataScheme;
import com.edsoft.storm.PrinterBolt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * Created by edsoft on 31.01.2016.
 *
 * Bu sınıfta storm topolojisi oluşturulmaktadır.
 */
@Component
public class Program {


    public void startDemos() {
/**
 * Kafka Configurasyonu
 */
        ZkHosts zkHosts = new ZkHosts("127.0.0.1:2181");
        String topicName = "cooja1";
        String consumerGroupId = "kafka-spark-streaming-example";
        String zookeeper_root = "";
        SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, topicName, zookeeper_root, consumerGroupId);

        kafkaConfig.scheme = new SchemeAsMultiScheme(new DataScheme());
        kafkaConfig.forceFromStart = true;

/**
 * Storm Topolojisinin oluşturulması
 */
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("KafkaSpout", new KafkaSpout(kafkaConfig), 1);
        builder.setBolt("PrinterBolt", new PrinterBolt()).globalGrouping("KafkaSpout");

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("KafkaConsumerTopology", config, builder.createTopology());

        try {
            Thread.sleep(600 * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        cluster.killTopology("KafkaConsumerTopology");
        cluster.shutdown();
        //System.out.println("Hello World!");
    }
}
