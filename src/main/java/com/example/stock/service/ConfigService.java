package com.example.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ConfigService {

	public void saveConfig(Map<String,Object> obj);
	
	public Map getConfig();
	
	public Object getConfigByName(String propertyName);
	
	public HashMap<String, Object> loadConfigFromFile();

	public HashMap<String, Object> getFile(String fileName);
	
	public List<HashMap<String, Object>> getFileAsList(String fileName);
}
