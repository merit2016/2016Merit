package com.edsoft.esper.handler.iot;


import com.edsoft.esper.subscriber.iot.*;
import com.edsoft.iot.Data;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * Created by edsoft on 13.01.2016.
 */
@Component
@Scope(value = "singleton")
public class DataEventHandler implements InitializingBean, Serializable {
    private EPServiceProvider epService;
    private EPStatement fallenStatement;
    private EPStatement needMedicalStatement;
    private EPStatement aHeartStatement;
    private EPStatement fastHeartBeatStatement;
    private EPStatement lowTemperatureStatement;
    private EPStatement heartAttackStatement;
    private RandomAccessFile randomAccessFile;
    String thread1 = "/home/edsoft/IdeaProjects/esper-demo-nuclear/src/main/resources/deneme_1.json";


    private static Logger LOG = LoggerFactory.getLogger(DataEventHandler.class);

    @Autowired
    @Qualifier("fallenEventSubscriber")
    private FallenEventSubscriber fallenEventSubscriber;

    @Autowired
    @Qualifier("needMedicalAttentionSubscriber")
    private NeedMedicalAttentionSubscriber needMedicalAttentionSubscriber;

    @Autowired
    @Qualifier("anormallyHeartSubscriber")
    private AnormallyHeartSubscriber anormallyHeartSubscriber;

    @Autowired
    @Qualifier("fastHeartBeat")
    private FastHeartBeat fastHeartBeat;

    @Autowired
    @Qualifier("lowTemperatureSubscriber")
    private LowTemperatureSubscriber lowTemperatureSubscriber;

    @Autowired
    @Qualifier("heartAttackSubscriber")
    private HeartAttackSubscriber heartAttackSubscriber;

    public void handle(Data event) {
        LOG.debug(event.toString());
        /*try {
            randomAccessFile = new RandomAccessFile(thread1, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        try {
            randomAccessFile.write((gson.toJson(event) + "\r\n").getBytes());
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        epService.getEPRuntime().sendEvent(event);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.debug("Configuring..");

        initService();
    }

    private void initService() {
        LOG.debug("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.edsoft.iot");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        createFallenEvent();
     /*   createNeedMedicalEvent();
        createAHeartEvent();
        createFastHeartBeat();
        createLowTemperatureEvent();
        createHeartAttackEvent();*/
    }

    private void createHeartAttackEvent() {
        LOG.debug("create heart attack");
        heartAttackStatement = epService.getEPAdministrator().createEPL(heartAttackSubscriber.getStatement());
        heartAttackStatement.setSubscriber(heartAttackSubscriber);
    }

    private void createLowTemperatureEvent() {
        LOG.debug("create low temperature");
        lowTemperatureStatement = epService.getEPAdministrator().createEPL(lowTemperatureSubscriber.getStatement());
        lowTemperatureStatement.setSubscriber(lowTemperatureSubscriber);
    }

    private void createFastHeartBeat() {
        LOG.debug("create Heart Attack");
        fastHeartBeatStatement = epService.getEPAdministrator().createEPL(fastHeartBeat.getStatement());
        fastHeartBeatStatement.setSubscriber(fastHeartBeat);

    }

    private void createNeedMedicalEvent() {
        LOG.debug("create Need Medical Check Expression");
        needMedicalStatement = epService.getEPAdministrator().createEPL(needMedicalAttentionSubscriber.getStatement());
        needMedicalStatement.setSubscriber(needMedicalAttentionSubscriber);
    }

    private void createFallenEvent() {
        LOG.debug("create Fallen Event Check Expression");
        fallenStatement = epService.getEPAdministrator().createEPL(fallenEventSubscriber.getStatement());
        fallenStatement.setSubscriber(fallenEventSubscriber);
    }

    private void createAHeartEvent() {
        LOG.debug("create AHeart event");
        aHeartStatement = epService.getEPAdministrator().createEPL(anormallyHeartSubscriber.getStatement());
        aHeartStatement.setSubscriber(anormallyHeartSubscriber);
    }
}
