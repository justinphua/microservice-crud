package com.cimb.codetest.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonLogger {

	public static JsonLogger instance;
	
	public static JsonLogger getInstance() {
		if(instance == null) {
			instance = new JsonLogger();
		}
		return instance;
	}
	
	public void printObjectInfo(Object obj) {
		ObjectMapper mapper = null;
        String result;
        
		try {
			mapper = new ObjectMapper();
	        mapper.enable(SerializationFeature.INDENT_OUTPUT);
			result = mapper.writeValueAsString(obj);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
