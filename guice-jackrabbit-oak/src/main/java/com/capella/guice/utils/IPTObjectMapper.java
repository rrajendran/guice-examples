package com.capella.guice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

public class IPTObjectMapper {
    private static ObjectMapper objectMapper;

    public IPTObjectMapper() {
    }

    public static ObjectMapper getInstance() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Jdk8Module());
            objectMapper.registerModule(new JSR310Module());
            objectMapper.registerModule(new JodaModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        return objectMapper;
    }
}