package com.newframe.core.hibernate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.newframe.core.vo.Detachable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Created by xm on 2016/4/2.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    @Override
    public void writeValue(File resultFile, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        if (value != null && value instanceof Detachable) {
            ((Detachable) value).detach();
        }
        super.writeValue(resultFile, value);
    }

    @Override
    public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        if (value != null && value instanceof Detachable) {
            ((Detachable) value).detach();
        }
        super.writeValue(jgen, value);
    }

    @Override
    public void writeValue(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        if (value != null && value instanceof Detachable) {
            ((Detachable) value).detach();
        }
        super.writeValue(out, value);
    }

    @Override
    public void writeValue(Writer w, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        if (value != null && value instanceof Detachable) {
            ((Detachable) value).detach();
        }
        super.writeValue(w, value);
    }

    @Override
    public String writeValueAsString(Object value) throws JsonProcessingException {
        if(value != null && value instanceof Detachable){
            ((Detachable) value).detach();
        }
        return super.writeValueAsString(value);
    }

    @Override
    public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        if (value != null && value instanceof Detachable) {
            ((Detachable) value).detach();
        }
        return super.writeValueAsBytes(value);
    }

    public HibernateAwareObjectMapper() {
        Hibernate4Module hibernate4Module = new Hibernate4Module();
        this.registerModule(hibernate4Module);
    }
}
