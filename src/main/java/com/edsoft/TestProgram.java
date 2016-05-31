package com.edsoft;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.edsoft.esper.handler.iot.DataEventHandler;
import com.edsoft.iot.Data;
import com.edsoft.kafka.DataScheme;
import com.edsoft.storm.PrinterBolt;
import com.edsoft.storm.PrinterSpout;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * Created by edsoft on 03.03.2016.
 */
public class TestProgram {
    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("PrinterSpout", new PrinterSpout(new Data[]{}), 1);
        builder.setBolt("PrinterBolt", new PrinterBolt()).shuffleGrouping("PrinterSpout");

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("Topology", config, builder.createTopology());

        try {
            Thread.sleep(600 * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        cluster.killTopology("Topology");
        cluster.shutdown();
        System.out.println("Hello World!");
    }
}
