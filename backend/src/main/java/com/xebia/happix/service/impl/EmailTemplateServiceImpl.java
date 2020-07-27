package com.xebia.happix.service.impl;

import com.xebia.happix.model.EmailTemplate;
import com.xebia.happix.repository.EmailTemplateRepository;
import com.xebia.happix.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Override
    public EmailTemplate save(EmailTemplate emailTemplate) {
        return emailTemplateRepository.save(emailTemplate);
    }

    @Override
    public List<EmailTemplate> findAll() {
        return emailTemplateRepository.findAll();
    }

    @Override
    public void delete(long id) {
        emailTemplateRepository.deleteById(id);
    }

    @Override
    public List<EmailTemplate> findByTemplateNameLike(String templateName) {
        return emailTemplateRepository.findByTemplateNameLike(templateName);
    }

    @Override
    public Optional<EmailTemplate> findOne(String templateName) {
        return emailTemplateRepository.findByTemplateName(templateName);
    }

    @Override
    public Optional<EmailTemplate> findById(Long id) {
        return emailTemplateRepository.findById(id);
    }
}
