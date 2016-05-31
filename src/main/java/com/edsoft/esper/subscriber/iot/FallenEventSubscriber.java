package com.edsoft.esper.subscriber.iot;


import com.edsoft.esper.subscriber.StatementSubscriber;
import com.edsoft.iot.Data;
import com.edsoft.iot.DataProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by edsoft on 13.01.2016.
 */
@Component
public class FallenEventSubscriber implements StatementSubscriber {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(FallenEventSubscriber.class);
    String fileName = "/home/user/Desktop/AAL-RTIS_STORMVersion/src/main/resources/esperRules/fallenRule.txt";
    RandomAccessFile randomAccessFile;

    public FallenEventSubscriber() {
        try {
            randomAccessFile = new RandomAccessFile(fileName, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        try {
            return randomAccessFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Data data) {

        // average temp over 10 secs

        DataProducer.sendKafkaFromEsper(data);

        /*StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [RESOURCES]  = ");
        sb.append("\n---------------------------------");
        sb.append("\n" + System.nanoTime());

        System.out.println(sb.toString());*/
    }
}
