package com.xebia.happix.controller;

import com.xebia.happix.model.EmailTemplate;
import com.xebia.happix.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class TemplateController {
    @Autowired
    private EmailTemplateService emailTemplateService;

    @GetMapping("/template")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmailTemplate>> getTemplateList(
            @RequestParam(value = "name", required = false) final String name) {
        if (name != null) {
            return ResponseEntity.ok().body(emailTemplateService.findByTemplateNameLike(name));
        } else {
            return ResponseEntity.ok().body(emailTemplateService.findAll());
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/template/{id}", method = RequestMethod.GET)
    public ResponseEntity<EmailTemplate> findbyId(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(emailTemplateService.findById(id).get());
    }

    @RequestMapping(value = "/template", method = RequestMethod.POST)
    public ResponseEntity<EmailTemplate> saveTemplate(@RequestBody EmailTemplate template) {
        return ResponseEntity.ok().body(emailTemplateService.save(template));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/template/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable(value = "id") Long id) {
        try {
            emailTemplateService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
