package com.edsoft.timetest;

import com.edsoft.iot.Data;
import com.edsoft.iot.DataProducer;
import com.edsoft.provenance.Little;
import com.google.gson.Gson;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.openprovenance.prov.model.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by edsoft on 12.01.2016.
 */
public class ThreadProducer {

    public static void main(String[] args) {
        // new ProducerThread();
        for (int i = 0; i < 100; i++) {
            new IoTProducer();
        }
        System.out.println("FINISH");
    }
}

class IoTProducer implements Runnable {
    Thread t;

    public IoTProducer() {
        this.t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 40000; i++) {
            // for (Data d : dataList) {
            Data d = new Data("1", String.valueOf(1), "D");
            //Document doc = Little.convertDocument(d);
            DataProducer.sendKafkaFromSensors(d);
            //System.out.println("Gönerildi");
        }
        System.out.println("finish ");
    }
}
