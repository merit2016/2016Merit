package com.edsoft.kafka;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.edsoft.iot.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 * Created by edsoft on 31.01.2016.
 * Kafka verilerinin storm üzerinde java sınıflarına döünştürülmesi
 */
public class DataScheme implements Scheme {


    @Override
    public List<Object> deserialize(byte[] ser) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new Values(mapper.readValue(ser, Data.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("objects");
    }
}
