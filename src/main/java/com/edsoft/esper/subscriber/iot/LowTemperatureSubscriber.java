package com.edsoft.esper.subscriber.iot;

import com.edsoft.SimpleProducer;
import com.edsoft.esper.subscriber.StatementSubscriber;
import com.edsoft.iot.Data;
import com.edsoft.iot.DataProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edsoft on 14.01.2016.
 */
@Component
public class LowTemperatureSubscriber implements StatementSubscriber {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(LowTemperatureSubscriber.class);
    String fileName = "/home/edsoft/IdeaProjects/AAL-RTIS/src/main/resources/esperRules/lowTemperature.txt";
    RandomAccessFile randomAccessFile;

    public LowTemperatureSubscriber() {
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

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Long eventMap, Data data) {

        //DataProducer.sendKafkaFromEsper(data);
        // average temp over 10 secs

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [HIGH TEMPERATURE]  = " + eventMap);
        sb.append("\n---------------------------------");
        sb.append("\n" + new SimpleDateFormat("mm:ss").format(new Date()));

        /*try {
            SimpleProducer.sendMail("Kalp Krizi");
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/

        LOG.debug(sb.toString());
    }
}
