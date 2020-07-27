package com.xebia.happix.service;

import com.xebia.happix.model.EmailTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmailTemplateService {
    EmailTemplate save(EmailTemplate emailTemplate);
    List<EmailTemplate> findAll();
    void delete(long id);
    List<EmailTemplate> findByTemplateNameLike(String templateName);
    Optional<EmailTemplate> findOne(String templateName);
    Optional<EmailTemplate> findById(Long id);
}
