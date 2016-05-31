package com.edsoft.esper;


import com.edsoft.esper.util.iot.IoTConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point for the Demo. Run this from your IDE, or from the command line using 'mvn exec:java'.
 */
public class StartDemo {

    /**
     * Logger
     */
    private static Logger LOG = LoggerFactory.getLogger(StartDemo.class);


    /**
     * Main method - start the Demo!
     */
    public static void main(String[] args) throws Exception {

        LOG.debug("Starting...");


        // Load spring config
       /* ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"application-context.xml"});
        BeanFactory factory = (BeanFactory) appContext;*/

        // Start Demo

        IoTConsumer consumer = new IoTConsumer();
        consumer.startSendingTemperatureReadings();


    }

}
