package com.xebia.happix.repository;

import com.xebia.happix.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    Optional<EmailTemplate> findByTemplateName(String templateName);

    List<EmailTemplate> findByTemplateNameLike(String templateName);
}
