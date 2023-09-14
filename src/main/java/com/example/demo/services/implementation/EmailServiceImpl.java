package com.example.demo.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	public JavaMailSender emailSender;
	
	public void sendNewMail(String to, String pass) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Changed password");
        message.setText("Your new password is " + pass);
        emailSender.send(message);
    }

}
