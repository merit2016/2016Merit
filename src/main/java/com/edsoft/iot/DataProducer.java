package com.edsoft.iot;

import com.google.gson.Gson;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.openprovenance.prov.model.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;

/**
 * Created by edsoft on 07.01.2016.
 */
public class DataProducer {
    private static Producer<String, Data> producer;
    private static final Properties properties;
    private static RandomAccessFile randomAccessFile;
    private static Gson gson;

    static {
        gson = new Gson();
        properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("request.required.acks", "1");
        properties.put("serializer.class", "com.edsoft.kafka.DataEncoder");
        producer = new Producer<>(new ProducerConfig(properties));
        try {
            randomAccessFile = new RandomAccessFile("/home/edsoft/AAL-RTIS/src/main/resources/test/test_result_new2.json", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void sendKafkaFromSensors(Data data) {
        //data.addTime();//Kafkaya giriş
        //System.out.println("SEND KAFKA FROM IOT SENSORS");
        //System.out.println();
        KeyedMessage<String, Data> iotData = new KeyedMessage<>("cooja1", data);
        producer.send(iotData);
    }

    public static void sendKafkaFromEsper(Data data) {
        //data.addTime();
       // convertJSON(data);
        KeyedMessage<String, Data> esperData = new KeyedMessage<>("esper1", data);
        producer.send(esperData);
    }

    private static void convertJSON(Data clickStream) {
        try {
            randomAccessFile.write((gson.toJson(clickStream) + "\r\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void provenanceToKafka(Document data) {
        KeyedMessage<String, Document> esperData = new KeyedMessage<>("aalT", data);
        producer.send(esperData);
    }*/
}
