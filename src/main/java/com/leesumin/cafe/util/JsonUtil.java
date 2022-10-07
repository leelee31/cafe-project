package com.leesumin.cafe.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JsonUtil {

    public static String objectToJsonMapper(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .findAndAddModules() // LocalDateTime 직렬화 문제
                .build();
        return mapper.writeValueAsString(obj);
    }
}