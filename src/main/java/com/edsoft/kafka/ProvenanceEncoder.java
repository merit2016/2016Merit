package com.edsoft.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.openprovenance.prov.model.Document;

import java.io.IOException;

/**
 * Created by edsoft on 17.02.2016.
 */
public class ProvenanceEncoder implements Encoder<Document>, Decoder<Document> {

    static ObjectMapper objectMapper = new ObjectMapper();

    public ProvenanceEncoder(VerifiableProperties verifiableProperties) {

    }

    @Override
    public byte[] toBytes(Document data) {
        try {
            return objectMapper.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "".getBytes();
    }

    @Override
    public Document fromBytes(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, Document.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}