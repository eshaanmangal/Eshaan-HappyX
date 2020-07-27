package com.xebia.happix.service.impl;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xebia.happix.config.EmailSender;
import com.xebia.happix.dto.EmailSenderRequest;
import com.xebia.happix.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailSender emailSender;
	
	private String _SUBJECT = "How are you feeling?";
	private String _TEXT = "How are you feeling?";
	
	@Override
	public void sendEmail(Set<String> emailIds, EmailSenderRequest request) throws Exception {
		Session session = emailSender.authenticate();
		
		if(request.getMessage() != null && !request.getMessage().isEmpty()) {
			_SUBJECT = request.getSubject();
			_TEXT = request.getMessage();
		}
		
		CompletableFuture.runAsync(() -> {
			emailIds.stream().forEach(recipient -> {
				emailSender.prepareAndSendMail(session, recipient, _SUBJECT, _TEXT, request.getTemplateId());
            });
        }).exceptionally(exc -> {
            exc.printStackTrace();
            return null;
        });
	}	
}
