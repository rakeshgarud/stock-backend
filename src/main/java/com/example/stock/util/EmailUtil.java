package com.example.stock.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {

	@Autowired
	JavaMailSender sender;
	
	public void sendMail() {
		
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo("nitindptl@gmail.com");
            helper.setText("Greetings :)");
            helper.setSubject("Mail From Spring Boot");
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
}
