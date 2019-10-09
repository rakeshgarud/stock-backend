package com.example.stock.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.example.stock.bean.ConfigData;
import com.example.stock.repo.ConfigRepository;
import com.example.stock.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
    private ResourceLoader resourceLoader;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Override
	public void saveConfig(Map<String,Object> obj) {

		try {
			
			ConfigData configData = getConfigData();
			HashMap<String, Object> existingData;
			if(configData.getData() == null) {
				existingData = new HashMap<String, Object>();
			}else {
				existingData = getExistingConfig(configData.getData());
			}
			existingData.putAll(obj);
			configData.setData(JsonUtil.mapToJsonString(existingData));
			configRepository.save(configData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, Object> getExistingConfig(String data) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map = (HashMap<String, Object>) JsonUtil.jsonStringToMap(data);
		return map;
	}
	
	private ConfigData getConfigData() {

		List<ConfigData> data = (List<ConfigData>) configRepository.findAll();
		if(data!=null && !data.isEmpty())
			return data.get(0);
		else
			return new ConfigData();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getConfig() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ConfigData> data = (List<ConfigData>) configRepository.findAll();
		if(data!=null && !data.isEmpty())
			map = (HashMap<String, Object>) JsonUtil.jsonStringToMap(data.get(0).getData());
		
		//ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		//File file = new File(classLoader.getResource("config.json").getFile());
		/*Resource res = resourceLoader.getResource("classpath:config.json");
		
		Map obj = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream stream  = res.getInputStream();
			obj = mapper.readValue(stream, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return map;
	}

	@Override
	public Object getConfigByName(String propertyName) {
		Map map = getConfig();
		return map.get(propertyName);
	}

	@Override
	public HashMap<String, Object> loadConfigFromFile() {
		Resource res = resourceLoader.getResource("classpath:config.json");
		HashMap<String, Object> obj = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream stream  = res.getInputStream();
			obj = (HashMap<String, Object>) mapper.readValue(stream, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public HashMap<String, Object> getFile(String fileName) {
		Resource res = resourceLoader.getResource("classpath:"+fileName);
		HashMap<String, Object> obj = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream stream  = res.getInputStream();
			obj = (HashMap<String, Object>) mapper.readValue(stream, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public List<HashMap<String, Object>> getFileAsList(String fileName) {
		Resource res = resourceLoader.getResource("classpath:"+fileName);
		List<HashMap<String, Object>> obj = new ArrayList<HashMap<String,Object>>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream stream  = res.getInputStream();
			obj = (List<HashMap<String, Object>>) mapper.readValue(stream, List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
