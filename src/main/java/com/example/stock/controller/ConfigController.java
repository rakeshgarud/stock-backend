package com.example.stock.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock.service.ConfigService;
import com.example.stock.util.EmailUtil;
import com.example.stock.util.EquityDerivativesUtil;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = { "Content-Type", "Accept",
		"x-xsrf-token", "Access-Control-Allow-Headers", "Origin", "Access-Control-Request-Method",
		"Access-Control-Request-Headers" })
@RestController
@RequestMapping("/config")
public class ConfigController {
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private EmailUtil emailSender;
	
	@GetMapping
	public Object getConfig() {
		//emailSender.sendMail();
		return configService.getConfig();
	}
	
	
	@PostMapping(value="",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void setConfig(@RequestBody Map<String,Object> obj) {
		try {
			configService.saveConfig(obj);
		} catch (Exception e) {
		}
	}
	
	@GetMapping(value="expiryDates")
	public Object getExpiryDates() {
		//emailSender.sendMail();
		return EquityDerivativesUtil.getExpiryDate(null);
	}
}
