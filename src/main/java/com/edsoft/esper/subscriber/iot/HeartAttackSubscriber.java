package com.edsoft.esper.subscriber.iot;


import com.edsoft.esper.subscriber.StatementSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edsoft on 14.01.2016.
 */
@Component
public class HeartAttackSubscriber implements StatementSubscriber {
    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(HeartAttackSubscriber.class);
    private String fileName = "/home/edsoft/IdeaProjects/AAL-RTIS/src/main/resources/esperRules/heartAttack.txt";
    private RandomAccessFile randomAccessFile;

    public HeartAttackSubscriber() {
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
    public void update(long c) {





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
        sb.append("\n* [ALERT] :HEART ATTACK " + "\t" + c);
        sb.append("\n* " + new SimpleDateFormat("mm:ss.SSS").format(new Date()));
        sb.append("\n***************************************");


        LOG.debug(sb.toString());
    }
}
