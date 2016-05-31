package com.edsoft.timetest;


import com.edsoft.iot.Data;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by edsoft on 14.01.2016.
 */
public class PerformanceTest {
    private RandomAccessFile randomAccessFile;
    private RandomAccessFile randomAccessFileTwo;
    private String fileName;
    private String fileNameTwo;
    private Gson gson;
    private static int FRC = 1000000;//661655
    private int f = 2;
    private int l = 0;

    // private double meanValue;

    public static void main(String[] args) throws IOException {
        PerformanceTest p = new PerformanceTest();

        p.messageRate();//ortalama mesaj sayısı
        // p.mean();//her sutünun ortalaması

        //p.lineCount();
    }

    public PerformanceTest() {

        //fileNameTwo = "/home/edsoft/IdeaProjects/esper-demo-nuclear/src/main/resources/deneme_1_part2.json";
        fileName = "/home/edsoft/AAL-RTIS/src/main/resources/test/test_result_new3.json";
        try {
            this.randomAccessFile = new RandomAccessFile(fileName, "r");
            //this.randomAccessFileTwo = new RandomAccessFile(fileNameTwo, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }//
    }

    public void lineCount() {
        int count = 1;
        while (true) {
            try {
                System.out.println(randomAccessFile.readLine());
                // randomAccessFile.seek(randomAccessFile.length() - 98);
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println(count);
            }
        }
    }

    public void messageRate() {
        try {
            gson = new Gson();
            randomAccessFile.readLine();
            String a = randomAccessFile.readLine();
            System.out.println(a);
            Data c = gson.fromJson(a, Data.class);
            randomAccessFile.seek(randomAccessFile.length() - (98));//98
            String s = randomAccessFile.readLine();
            System.out.println(s);
            Data d = gson.fromJson(s, Data.class);
            System.out.println(d.getTimeList().get(f) + "\t" + c.getTimeList().get(l));
            System.out.println("Sonuc : " + (((d.getTimeList().get(f) - c.getTimeList().get(l)) / 1000000000.0)));
            System.out.println("Bir saniyede gelen olay sayısı : " + FRC / ((d.getTimeList().get(f) - c.getTimeList().get(l)) / 1000000000.0));
            double middleResult = (d.getTimeList().get(f) - c.getTimeList().get(l)) / FRC;
            System.out.println("Hocanın istediği -> " + (middleResult / 1_000_000.0) + " sn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson = new Gson();
    }

    public void mean() throws IOException {
        long t1t2;
        long t2t3;
        long t3t1;
        double sum1 = 0;
        double sum2 = 0;
        //double sum3 = 0;
        randomAccessFile = new RandomAccessFile(fileName, "r");

        while (true) {
            try {
                String a = randomAccessFile.readLine();
                Data c = gson.fromJson(a, Data.class);
                t1t2 = c.getTimeList().get(1) - c.getTimeList().get(0);
                t2t3 = c.getTimeList().get(2) - c.getTimeList().get(1);
                // t3t1 = c.getTimeList().get(3) - c.getTimeList().get(2);
                sum1 += t1t2;
                sum2 += t2t3;
                //sum3 += t3t1;
                //meanValue = (sum1 / FRC) / 1000000000;
            } catch (NullPointerException ex) {
                System.out.println("AVG -> " + (sum1 / FRC) / 1_000_000_000 + "\t" + (sum2 / FRC) / 1_000_000_000 + "\t"
                      /*  (sum3 / FRC) / 1_000_000_000)*/);
                System.exit(0);
            }
        }
    }
}
