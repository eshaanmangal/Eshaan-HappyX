package com.xebia.happix.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.xebia.happix.dto.EmailSenderRequest;

public interface EmailService {

	public void sendEmail(Set<String> emailIds, EmailSenderRequest request) throws Exception;
}
