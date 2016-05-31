package com.edsoft.esper.util.iot;


import com.edsoft.esper.handler.iot.DataEventHandler;
import com.edsoft.iot.Data;
import com.edsoft.provenance.Little;
import com.google.gson.Gson;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.openprovenance.prov.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by edsoft on 13.01.2016.
 */
public class IoTConsumer {
    private static Logger LOG = LoggerFactory.getLogger(IoTConsumer.class);
    final static String TOPIC = "aalTwo";
    private RandomAccessFile randomAccessFile;
    private String fileName = "/home/edsoft/IdeaProjects/esper-demo-nuclear/src/main/resources/deneme_1.json";
    ConsumerConnector consumerConnector;
    private Gson gson;


    /**
     * The TemperatureEventHandler - wraps the Esper engine and processes the Events
     */


    private DataEventHandler dataEventHandler;

    public IoTConsumer() {
        try {
            randomAccessFile = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        gson = new Gson();
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("request.required.acks", "1");
        properties.put("zookeeper.connect", "localhost:2181");
        properties.put("group.id", "kafka-spark-streaming-example");
        properties.put("serializer.class", "com.edsoft.kafka.ProvenanceEncoder");//Düzenle
        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
    }

    /**
     * Creates simple random Temperature events and lets the implementation class handle them.
     */
    public void startSendingTemperatureReadings() {

        dataEventHandler = new DataEventHandler();

        ExecutorService xrayExecutor = Executors.newFixedThreadPool(5);


        //Kafka Consumer buraya eklenecek111
        xrayExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Map<String, Integer> topicCountMap = new HashMap<>();
                topicCountMap.put(TOPIC, 1);
                Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
                KafkaStream<byte[], byte[]> stream = consumerMap.get(TOPIC).get(0);
                for (MessageAndMetadata<byte[], byte[]> aStream : stream) {

                    /*Data data = gson.fromJson(new String(aStream.message()), Data.class);
                    data.addTime();*/
                    Document document = gson.fromJson(new String(aStream.message()), Document.class);
                    Data dEsper = Little.convertData(document);

                    System.out.println("Data in Esper");

                /*try {
                    randomAccessFile.write((gson.toJson(data) + "\r\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                    dataEventHandler.handle(dEsper);
                }
            }
        });
    }


    private String getStartingMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n**************s**********************************************");
        sb.append("\n* STARTING - ");
        sb.append("\n* PLEASE WAIT - TEMPERATURES ARE RANDOM SO MAY TAKE");
        sb.append("\n* A WHILE TO SEE WARNING AND CRITICAL EVENTS!");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
