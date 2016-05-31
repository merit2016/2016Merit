package com.edsoft.esper.subscriber.iot;

import com.edsoft.SimpleProducer;
import com.edsoft.esper.subscriber.StatementSubscriber;
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
 * Created by edsoft on 13.01.2016.
 */
@Component
public class FastHeartBeat implements StatementSubscriber {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(FastHeartBeat.class);
    private String fileName = "/home/edsoft/IdeaProjects/AAL-RTIS/src/main/resources/esperRules/fastHeartBeat.txt";
    private RandomAccessFile randomAccessFile;

    public FastHeartBeat() {
        try {
            randomAccessFile = new RandomAccessFile(fileName, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used as the minimum starting threshold for a critical event.
     */


    /**
     * If the last event in a critical sequence is this much greater than the first - issue a
     * critical alert.
     */


    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example using 'Match Recognise' syntax.

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
    public void update(double a, long eventMap) {


      /*  try {
            SimpleProducer.sendMail("Hastada gün içinde çarpıntılar meydana geldi. " +
                    "Lütfen kontrol ediniz. ");
            SimpleProducer.sendSMS("Hasta Kalp Krizi geçiriyor. Lütfen hastaneyi arayın.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/

       /* // 1st Temperature in the Critical Sequence
        TemperatureEvent temp1 = (TemperatureEvent) eventMap.get("temp1");
        // 2nd Temperature in the Critical Sequence
        TemperatureEvent temp2 = (TemperatureEvent) eventMap.get("temp2");
        // 3rd Temperature in the Critical Sequence
        TemperatureEvent temp3 = (TemperatureEvent) eventMap.get("temp3");
        // 4th Temperature in the Critical Sequence
        TemperatureEvent temp4 = (TemperatureEvent) eventMap.get("temp4");*/

        StringBuilder sb = new StringBuilder();
        sb.append("***************************************");
        sb.append("\n* [ALERT] : FAST HEART BEAT " + "\t" + a + "\t" + eventMap);
        sb.append("\n* " + new SimpleDateFormat("mm:ss.SSS").format(new Date()));
        sb.append("\n***************************************");

        LOG.debug(sb.toString());
    }

}


