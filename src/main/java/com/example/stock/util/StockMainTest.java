package com.example.stock.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

	public static void main(String[] args) throws ParseException, Exception {
		//sendMail();
		/*int[] numbers = {11200,11250,11300,11350,11400,11450,11500,11550,11600,11650,11700};
		List<Integer> list = Arrays.stream(numbers).boxed().collect(Collectors.toList());

		double n = 11478.55;

		int c = list.stream()
		            .min(Comparator.comparingInt(i -> Math.abs(i - (int)n))).orElse(0);
		System.out.println(c);*/
		
		double X[] = {19.8,
				19.8
}; 
        double Y[] = {11500.8,
        		115500.0,116600
}; 
       
        // Find the size of array. 
        int n = X.length; 
       
        // Function call to correlationCoefficient. 
        System.out.printf("%6f", 
                 correlationCoefficient(X, Y)); 
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
	
	// function that returns correlation coefficient. 
    static float correlationCoefficient(double X[], 
                                    double Y[]) 
    { 
    int n = X.length;
    	double sum_X = 0, sum_Y = 0, sum_XY = 0; 
    	double squareSum_X = 0, squareSum_Y = 0; 
       
        for (int i = 0; i < n; i++) 
        { 
            // sum of elements of array X. 
            sum_X = sum_X + X[i]; 
       
            // sum of elements of array Y. 
            sum_Y = sum_Y + Y[i]; 
       
            // sum of X[i] * Y[i]. 
            sum_XY = sum_XY + X[i] * Y[i]; 
       
            // sum of square of array elements. 
            squareSum_X = squareSum_X + X[i] * X[i]; 
            squareSum_Y = squareSum_Y + Y[i] * Y[i]; 
        } 
       
        // use formula for calculating correlation  
        // coefficient. 
        float corr = (float)(n * sum_XY - sum_X * sum_Y)/ 
                     (float)(Math.sqrt((n * squareSum_X - 
                     sum_X * sum_X) * (n * squareSum_Y -  
                     sum_Y * sum_Y))); 
       
        return corr; 
    } 
   
	
}
