package com.ethanpritchard.example.dagger.modules;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonModule {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ObjectMapper getOBJECT_MAPPER() {
        return OBJECT_MAPPER;
    }
}
