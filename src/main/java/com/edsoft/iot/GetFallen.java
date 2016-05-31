package com.edsoft.iot;

import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;
import com.edsoft.provenance.Little;
import org.openprovenance.prov.model.Document;

/**
 * Created by edsoft on 08.01.2016.
 */
public class GetFallen {

    private static String url = "coap://[aaaa::c30c:0:0:2]:5683/test/fallen";

    /*
     * Application entry point.
     *
     */
    public static void main(String args[]) {

        Request request = Request.newGet();

        // specify URI of target endpoint
        request.setURI(url);//URI
        request.setObserve();
        int count = 0;
        try {
            while (true) {//For observer, I use while(true)
                request.send();
                Response response = request.waitForResponse(2000);
                if (null != response) {
                    String responseString = response.toString();//GET DATA
                    if (responseString.startsWith("NON")) {
                        String[] values = responseString.split("\"");
                        String[] values2 = values[1].split(",");
                        // response received, output a pretty-print
                        System.out.println(response);
                        Data d = new Data(values2[0], values2[1], values2[2]);
                        //Document document = Little.convertDocument(d);
                        //System.out.println(document);
                        //ProvenanceProducer.provenanceToKafka(document);
                        DataProducer.sendKafkaFromSensors(d);
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
