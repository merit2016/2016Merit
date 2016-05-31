package com.edsoft.kafka;

import com.edsoft.iot.Data;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Namespace;
import org.openprovenance.prov.model.StatementOrBundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by edsoft on 01.02.2016.
 */
public class KafkaConsumer extends Thread {
    final static String TOPIC = "RESOURCES5";
    ConsumerConnector consumerConnector;
    private RandomAccessFile randomAccessFile;


    public static void main(String[] argv) throws UnsupportedEncodingException {
        KafkaConsumer helloKafkaConsumer = new KafkaConsumer();
        helloKafkaConsumer.start();
    }

    public KafkaConsumer() {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("request.required.acks", "1");
        properties.put("zookeeper.connect", "localhost:2181");
        properties.put("group.id", "kafka-spark-streaming-example");
        properties.put("serializer.class", "com.edsoft.kafka.DataEncoder");
        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        try {
            randomAccessFile = new RandomAccessFile("/home/edsoft/AAL-RTIS/src/main/resources/test/test_result.json", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(TOPIC, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consumerMap.get(TOPIC).get(0);
        for (MessageAndMetadata<byte[], byte[]> aStream : stream) {
            Gson gson = new Gson();
            //  Data data = gson.fromJson(new String(aStream.message()), Data.class);


            Document data = gson.fromJson(new String(aStream.message()), Document.class);


            try {
                randomAccessFile.write((gson.toJson(data) + "\r\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            /// System.out.println("consumer is fine")
            //System.out.println(clickStream);

        }
    }
}
