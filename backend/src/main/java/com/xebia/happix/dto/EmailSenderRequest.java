package com.xebia.happix.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ApiModel(value = "emailSenderRequest", description = "Request parameters to send the email for selected department or location or given email ids")
public class EmailSenderRequest {
	@ApiParam(allowMultiple = true, required = false, type = "String", value = "abc@xyz.com.")
	private List<String> emails;

	@ApiParam(allowMultiple = true, required = false, type = "String", value = "names")
	private List<String> names;

	@ApiParam(allowMultiple = true, required = false, type = "String", value = "department name")
	private List<String> departments;

	@ApiParam(allowMultiple = true, required = false, type = "String", value = "location")
	private List<String> locations;

	@ApiParam(allowMultiple = false, required = false, type = "String", value = "templateID")
	private String templateId;

	@ApiParam(allowMultiple = false, required = false, type = "String", value = "Message")
	private String message;

	@ApiParam(allowMultiple = false, required = false, type = "String", value = "Subject")
	private String subject;

}
