package com.example.stock.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.MimeMessage;

import org.apache.http.ParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.EquityDerivativePredicate;
import com.example.stock.bean.Stock;
import com.example.stock.bean.StockPredicate;
import com.example.stock.enums.Column;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StockMainTest {

	public static final String SAMPLE_CSV_PATH = "D:\\GIT\\sec_bhavdata_full-21st Aug.csv";

	public static List<Stock> stocks = new ArrayList<Stock>();
	private static String DIR = "D:\\GIT";

	public static void main(String[] args) throws ParseException, IOException {
		//sendMail();
		
	}

	
	private static void sendMail() {
		JavaMailSender sender = new JavaMailSenderImpl();
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo("nitindptl@gmail.com");
            helper.setText("Greetings :)");
            helper.setSubject("Mail From Spring Boot");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.send(message);
	}
	
}