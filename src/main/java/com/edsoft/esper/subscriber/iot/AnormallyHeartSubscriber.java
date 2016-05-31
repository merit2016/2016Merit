package com.edsoft.esper.subscriber.iot;

import com.edsoft.esper.subscriber.StatementSubscriber;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edsoft on 13.01.2016.
 */
@Component
public class AnormallyHeartSubscriber implements StatementSubscriber {

    private static Logger LOG = LoggerFactory.getLogger(FallenEventSubscriber.class);
    String fileName = "/home/edsoft/IdeaProjects/AAL-RTIS/src/main/resources/esperRules/anormallyHeart.txt";
    String thread1 = "/home/edsoft/IdeaProjects/esper-demo-nuclear/src/main/resources/deneme_1_part2.json";
    RandomAccessFile randomAccessFile;
    RandomAccessFile randomAccessFileTwo;
    Gson gson;
    int count = 0;

    public AnormallyHeartSubscriber() {
        try {
            gson = new Gson();
            randomAccessFile = new RandomAccessFile(fileName, "r");
            randomAccessFileTwo = new RandomAccessFile(thread1, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getStatement() {
        try {
            return randomAccessFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Integer val) {


        //   if (eventMap > 70) {


        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [AHEART]  = " + val);
        sb.append("\n---------------------------------");
        sb.append("\n" + new SimpleDateFormat("mm:ss").format(new Date()));

        LOG.debug(sb.toString());
    }
}
