package com.edsoft.iot;

import com.edsoft.kafka.DataEncoder;
import com.edsoft.provenance.Little;
import org.openprovenance.prov.model.Document;

/**
 * Created by edsoft on 01.04.2016.
 */
public class X {
    public static void main(String[] args) {
        int count = 0;
        while (true) {
            Data d = new Data(String.valueOf(count), "1", "D");
            DataProducer.sendKafkaFromSensors(d);
            count++;
        }
    }
}
