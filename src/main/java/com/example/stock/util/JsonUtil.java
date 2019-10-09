package com.example.stock.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static Map jsonToObject(InputStream jsonFile, Map obj) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			obj = mapper.readValue(jsonFile, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public static Map jsonStringToMap(String jsonStr) {
		ObjectMapper mapperObj = new ObjectMapper();
		Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            resultMap = mapperObj.readValue(jsonStr, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultMap;
	}
	
	public static String mapToJsonString(Map map) {
		ObjectMapper mapperObj = new ObjectMapper();
		String resultMap = "{}";
        try {
            resultMap = mapperObj.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultMap;
	}
	
	private static String[] extractKeys(String path) throws Exception {
	    String leadingSlash = "/";
	    if (!path.startsWith(leadingSlash)) throw new Exception("Path must begin with a leading '/'");
	  
	    return path.substring(1).split(leadingSlash);
	}
	
	public static String getByJsonPath(JSONObject jsonObject, String path) {
		try {
			String[] keys = extractKeys(path);
			return getValueFromJsonObjectGivenKeys(jsonObject,keys);
		} catch (Exception e) {
			
		}
		return path;
	}
	private static String getValueFromJsonObjectGivenKeys(JSONObject jsonObject, String[] keys) throws Exception {
	    String currentKey = keys[0];

	    if (keys.length == 1 && jsonObject.has(currentKey)) {
	      return jsonObject.getString(currentKey);
	    } else if (!jsonObject.has(currentKey)) {
	      throw new Exception(currentKey + "is not a valid key.");
	    }

	    JSONObject nestedJsonObjectVal = jsonObject.getJSONObject(currentKey);
	    int nextKeyIdx = 1;
	    String[] remainingKeys = Arrays.copyOfRange(keys, nextKeyIdx, keys.length);
	    return getValueFromJsonObjectGivenKeys(nestedJsonObjectVal, remainingKeys);
	  }
}
