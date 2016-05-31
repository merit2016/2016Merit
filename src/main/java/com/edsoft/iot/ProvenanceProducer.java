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
 * Created by user on 3/3/16.
 */
public class ProvenanceProducer {
    private static Producer<String, Document> producer;
    private static final Properties properties;
    static {
        properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("request.required.acks", "1");
        properties.put("serializer.class", "com.edsoft.kafka.ProvenanceEncoder");
        producer = new Producer<>(new ProducerConfig(properties));
    }

    public static void provenanceToKafka(Document data) {
        KeyedMessage<String, Document> esperData = new KeyedMessage<>("pro", data);
        producer.send(esperData);
    }
}
