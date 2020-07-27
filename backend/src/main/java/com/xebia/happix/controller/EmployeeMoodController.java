package com.xebia.happix.controller;

import com.xebia.happix.CSVUtil.CSVDownloader;
//import com.xebia.happix.application.service.TemplateService;
import com.xebia.happix.dto.EmployeeMoodChartData;
import com.xebia.happix.dto.EmployeeMoodResponse;
import com.xebia.happix.dto.MoodStatistics;
import com.xebia.happix.model.EmailTemplate;
import com.xebia.happix.model.EmployeeMood;
import com.xebia.happix.service.EmailTemplateService;
import com.xebia.happix.service.EmployeeMoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Slf4j
public class EmployeeMoodController {

    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)

    @Autowired
    private EmployeeMoodService employeeMoodService;

    @Autowired
    private EmailTemplateService emailTemplateService;

//    @Autowired
//    private TemplateService templateService;

    @GetMapping("/chart")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeMoodChartData> getAllMoodChart(
            @RequestParam(value = "start", required = false) final String start,
            @RequestParam(value = "end", required = false) final String end,
            @RequestParam(value = "templateID", required = false) final String tempalteID) {
        return ResponseEntity.ok().body(employeeMoodService.getEmployeeMoodStatistics());
    }


    @GetMapping("/chartByDate")
    // @PreAuthorize("hasRole('ADMIN')")
    public TreeMap<LocalDate, List<MoodStatistics>> getAllMoodChartByDate(
            @RequestParam(value = "startDate", required = true)
            @DateTimeFormat(iso = ISO.DATE) final LocalDate startDate,
            @RequestParam(value = "endDate", required = true)
            @DateTimeFormat(iso = ISO.DATE) final LocalDate endDate,
            @RequestParam(value = "templateID", required = true) final String templateID) {
        return (employeeMoodService.getEmployeeMoodStatisticsbyDate(templateID, startDate, endDate));
    }


    @GetMapping("/mood")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllEmployeeMood(@RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "1000000") Integer pageSize,
                                             @RequestParam(value = "email", required = false) final String email,
                                             @RequestParam(value = "start", required = false) final String start,
                                             @RequestParam(value = "end", required = false) final String end) {
        if (email != null) {
            return ResponseEntity.ok().body(employeeMoodService.getAllEmployeesByEmail(pageNo, pageSize, email));
        } else if (start != null || end != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
            return ResponseEntity.ok()
                    .body(employeeMoodService.getAllEmployeeBetween(startDateTime, endDateTime, pageNo, pageSize));
        } else {
            return ResponseEntity.ok().body(employeeMoodService.getAllEmployees(pageNo, pageSize));
        }
    }

//    @GetMapping("/templatesList")
//    public List<String> getAllTemplates() {
//        return templateService.getAllTemplateId();
//    }
//
//    @GetMapping("/TemplateMood")
//    public Map<String, List<String>> getTemplateMoods() {
//        return templateService.getTemplateIDwithMoods();
//    }

    @GetMapping("/moodcollector")
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<EmployeeMood> createAllEmployeeMood(
            @RequestParam(value = "email", required = true) final String email,
            @RequestParam(value = "moodType", required = true) final String moodType,
            @RequestParam(value = "templateId", required = true) final String templateId) {
        Optional<EmailTemplate> emailTemplate = emailTemplateService.findOne(templateId);
        if (emailTemplate.isPresent()) {
            if (!emailTemplate.get().getMoods().contains(moodType)) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("X-info", "Mood Type Does Not Belong to provided template");
				return ResponseEntity.ok().headers(headers).build();
            }
        } else {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-info", "Template Id Does not Exist !");
			return ResponseEntity.ok().headers(headers).build();
        }
        return ResponseEntity.ok().body(employeeMoodService.save(email, moodType, templateId));
    }

    @GetMapping("/moodDownload")
    // @PreAuthorize("hasRole('ADMIN')")
    public void getAllEmployeeMoodDownload(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10000000") Integer pageSize,
                                           @RequestParam(value = "email", required = false) final String email,
                                           @RequestParam(value = "start", required = false) final String start,
                                           @RequestParam(value = "end", required = false) final String end,
                                           @RequestParam(value = "fileName", required = false) final String fileName,
                                           @ApiIgnore final HttpServletResponse response) {
        if (email != null) {
            Page<EmployeeMood> pageEmpMood = employeeMoodService.getAllEmployeesByEmail(pageNo, pageSize, email);
            List<EmployeeMood> empMoodList = pageEmpMood.getContent();
            List<EmployeeMoodResponse> respList = empMoodList.stream().map(EmployeeMoodResponse::of)
                    .collect(Collectors.toList());
            try {
                CSVDownloader.beanToCSV(respList, fileName, response);
            } catch (Exception e) {
                log.error("Error while generating CSV file : " + e.getMessage());
            }

        } else if (start != null || end != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

            Page<EmployeeMood> pageEmpMood = employeeMoodService.getAllEmployeeBetween(startDateTime, endDateTime,
                    pageNo, pageSize);
            List<EmployeeMood> empMoodList = pageEmpMood.getContent();
            List<EmployeeMoodResponse> respList = empMoodList.stream().map(EmployeeMoodResponse::of)
                    .collect(Collectors.toList());
            try {
                CSVDownloader.beanToCSV(respList, fileName, response);
            } catch (Exception e) {
                log.error("Error while generating CSV file : " + e.getMessage());
            }
        } else {
            Page<EmployeeMood> pageEmpMood = employeeMoodService.getAllEmployees(pageNo, pageSize);
            List<EmployeeMood> empMoodList = pageEmpMood.getContent();
            List<EmployeeMoodResponse> respList = empMoodList.stream().map(EmployeeMoodResponse::of)
                    .collect(Collectors.toList());
            try {
                CSVDownloader.beanToCSV(respList, fileName, response);
            } catch (Exception e) {
                log.error("Error while generating CSV file : " + e.getMessage());
            }
        }
    }

}
