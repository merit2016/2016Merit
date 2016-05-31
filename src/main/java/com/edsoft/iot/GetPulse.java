package com.edsoft.iot;

import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;

/**
 * Created by edsoft on 07.01.2016.
 */
public class GetPulse {

    //adresi değiştir
    private static String url = "coap://[aaaa::c30c:0:0:4]:5683/test/pulse";//4

    /*
     * Application entry point.
     *
     */
    public static void main(String args[]) {

        Request request = Request.newGet();

        // specify URI of target endpoint
        request.setURI(url);
        request.setObserve();
        try {
            while (true) {
                request.send();
                Response response = request.waitForResponse(2000);
                if (null != response) {
                    String responseString = response.toString();
                    if (responseString.startsWith("NON")) {
                        String[] values = responseString.split("\"");
                        String[] values2 = values[1].split(",");
                        // response received, output a pretty-print
                        System.out.println(response);
                        Data d = new Data(values2[0], values2[1], values2[2]);
                       // DataProducer.sendKafkaFromSensors(d);
                    } else {
                        System.out.println("No response received.");
                    }
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Receiving of response interrupted: " + e.getMessage());
            System.exit(-1);
        }

    }
}
