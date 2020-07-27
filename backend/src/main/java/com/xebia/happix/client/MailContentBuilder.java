package com.xebia.happix.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

	private TemplateEngine templateEngine;

	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String build(String email, String templateId) {
		String template = templateId == null ? "MailTemplate" : "MailTemplate2";
		Context context = new Context();
		context.setVariable("EMPLOYEE_EMAIL", email);
		String content = templateEngine.process(template, context).replace("${EMPLOYEE_EMAIL}", email);
		return content;
	}
	
	public String build(String email, String templateId, String text) {
		String template = templateId == null || templateId.isEmpty() ? "MailTemplate" : "MailTemplate" + templateId;
		Context context = new Context();
		context.setVariable("EMPLOYEE_EMAIL", email);
		String content = templateEngine.process(template, context).replace("${EMPLOYEE_EMAIL}", email)
				.replace("${TEXT}", text);
		return content;
	}
}
