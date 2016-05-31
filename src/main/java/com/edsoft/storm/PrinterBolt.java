package com.edsoft.storm;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.utils.Utils;
import com.edsoft.Program;
import com.edsoft.esper.subscriber.iot.FallenEventSubscriber;
import com.edsoft.esper.subscriber.iot.LowTemperatureSubscriber;
import com.edsoft.esper.subscriber.iot.NeedMedicalAttentionSubscriber;
import com.edsoft.iot.Data;
import com.espertech.esper.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by edsoft on 21.01.2016.
 */

public class PrinterBolt extends BaseBasicBolt {

    private EPServiceProvider epService;


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
        this.setupEsper();
    }

    /**
     * Esper Konfigurasyonu
     */
    private void setupEsper() {
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.edsoft.iot");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        createFallenEvent();
        //createLowTemperatureEvent();
    }

    private void createFallenEvent() {
        FallenEventSubscriber fallenEventSubscriber = new FallenEventSubscriber();
        EPStatement fallenStatement = epService.getEPAdministrator().createEPL(fallenEventSubscriber.getStatement());
        fallenStatement.setSubscriber(fallenEventSubscriber);
    }

    private void createLowTemperatureEvent() {
        LowTemperatureSubscriber lowTemperatureSubscriber = new LowTemperatureSubscriber();
        EPStatement lowTemperatureStatement = epService.getEPAdministrator().createEPL(lowTemperatureSubscriber.getStatement());
        lowTemperatureStatement.setSubscriber(lowTemperatureSubscriber);
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            if (null != input.getValue(0)) {
                //System.out.println(input.getString(0));
                Data data = (Data) input.getValue(0);
                //data.addTime();//Kafka2Storm2Esper
                epService.getEPRuntime().sendEvent(data);
            }
        } catch (NullPointerException ex) {
            System.out.println("null");
        }
        // dataEventHandler.handle(App.changeFormat(input.getString(0)));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
