package com.xebia.happix.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.xebia.happix.model.EmployeeMood;
import com.xebia.happix.model.MoodType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeMoodResponse {

	public static DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private long id;

	@CsvBindByName(column = "Email")
	@CsvBindByPosition(position = 0)
	private String email;

	@CsvBindByName(column = "MoodType")
	@CsvBindByPosition(position = 1)
	private String moodType;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	@CsvBindByName(column = "Created_Date")
	@CsvBindByPosition(position = 2)
	private String createdDateString;

	@CsvBindByName(column = "Updated_Date")
	@CsvBindByPosition(position = 3)
	private String updatedDateString;

	public static EmployeeMoodResponse of(EmployeeMood empMood) {
		EmployeeMoodResponse resp = new EmployeeMoodResponse();
		resp.setId(empMood.getId());
		resp.setEmail(empMood.getEmail());
		resp.setMoodType(empMood.getMoodType());
		resp.setCreatedDate(empMood.getCreatedDate());
		resp.setUpdatedDate(empMood.getUpdatedDate());
		resp.setCreatedDateString(formatDateTime(empMood.getCreatedDate()));
		resp.setUpdatedDateString(formatDateTime(empMood.getUpdatedDate()));

		return resp;
	}

	public static String formatDateTime(final LocalDateTime datetime) {
		return datetime.format(DEFAULT_DATE_TIME_FORMATTER);
	}
}
