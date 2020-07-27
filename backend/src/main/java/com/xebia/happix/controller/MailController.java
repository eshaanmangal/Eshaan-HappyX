package com.xebia.happix.controller;

import com.xebia.happix.application.service.MasterDataService;
import com.xebia.happix.config.EmailSender;
import com.xebia.happix.dto.EmailSenderRequest;
import com.xebia.happix.model.EmployeeMood;
import com.xebia.happix.model.EmployeeResponse;
import com.xebia.happix.model.MoodType;
import com.xebia.happix.service.EmailService;
import com.xebia.happix.service.EmployeeMoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Slf4j
public class MailController {

    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmployeeMoodService employeeMoodService;

    @Autowired
    private EmailSender emailSender;


    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sendmail")
    public void sendMail(@RequestParam(value = "emails", required = false) final List<String> emails,
                         @RequestParam(value = "names", required = false) final List<String> names,
                         @RequestParam(value = "departments", required = false) final List<String> departments,
                         @RequestParam(value = "locations", required = false) final List<String> locations) {

        Set<String> emailsList = new HashSet<>();

        if (emails != null) {
            emailsList.addAll(emails);
        }
        if (names != null && names.size() > 0) {
            names.stream().forEach(name -> {
                Set<EmployeeResponse> deptEmpList = masterDataService.getEmployeeListByDepartmentWise(name);
                if (deptEmpList != null && deptEmpList.size() > 0) {
                    emailsList.addAll(deptEmpList.stream().map(employeeResponse -> employeeResponse.getStaffEmailId())
                            .collect(Collectors.toSet()));
                } else {
                    Set<EmployeeResponse> locEmpList = masterDataService.getEmployeeListByLoctionWise(name);
                    emailsList.addAll(locEmpList.stream().map(employeeResponse -> employeeResponse.getStaffEmailId())
                            .collect(Collectors.toSet()));
                }
            });
        }

        if (departments != null && departments.size() > 0) {
            departments.stream().forEach(dept -> {
                Set<String> deptEmpList = masterDataService.getEmployeeListByDepartmentWise(dept).stream()
                        .map(employeeResponse -> employeeResponse.getStaffEmailId()).collect(Collectors.toSet());
                emailsList.addAll(deptEmpList);
            });
        }
        if (locations != null && locations.size() > 0) {
            locations.stream().forEach(location -> {
                Set<String> locationwiseEmpList = masterDataService.getEmployeeListByLoctionWise(location).stream()
                        .map(employeeResponse -> employeeResponse.getStaffEmailId()).collect(Collectors.toSet());
                emailsList.addAll(locationwiseEmpList);
            });
        }
        log.info("Total Emails size " + emailsList.size());
        emailsList.parallelStream()
                .forEach(email -> emailSender.setEmail(email, "How are you feeling?", "How are you feeling?", null));

        emailsList.stream().forEach(email -> employeeMoodService.save(EmployeeMood.of(email, MoodType.NO_RESPONSE.name())));
    }

    @PostMapping("/postemail")
    public ResponseEntity<String> postEmail(@RequestBody EmailSenderRequest request) {
        Set<String> emailsList = new HashSet<>();

        if (request.getEmails() != null) {
            emailsList.addAll(request.getEmails());
        }
        if (request.getNames() != null && request.getNames().size() > 0) {
            request.getNames().stream().forEach(name -> {
                Set<EmployeeResponse> deptEmpList = masterDataService.getEmployeeListByDepartmentWise(name);
                if (deptEmpList != null && deptEmpList.size() > 0) {
                    emailsList.addAll(deptEmpList.stream().map(employeeResponse -> employeeResponse.getStaffEmailId())
                            .collect(Collectors.toSet()));
                } else {
                    Set<EmployeeResponse> locEmpList = masterDataService.getEmployeeListByLoctionWise(name);
                    if (locEmpList != null) {
                        emailsList
                                .addAll(locEmpList.stream().map(employeeResponse -> employeeResponse.getStaffEmailId())
                                        .collect(Collectors.toSet()));
                    }
                }
            });
        }

        if (request.getDepartments() != null && request.getDepartments().size() > 0) {
            request.getDepartments().stream().forEach(dept -> {
                Set<String> deptEmpList = masterDataService.getEmployeeListByDepartmentWise(dept).stream()
                        .map(employeeResponse -> employeeResponse.getStaffEmailId()).collect(Collectors.toSet());
                emailsList.addAll(deptEmpList);
            });
        }
        if (request.getLocations() != null && request.getLocations().size() > 0) {
            request.getLocations().stream().forEach(location -> {
                Set<String> locationwiseEmpList = masterDataService.getEmployeeListByLoctionWise(location).stream()
                        .map(employeeResponse -> employeeResponse.getStaffEmailId()).collect(Collectors.toSet());
                emailsList.addAll(locationwiseEmpList);
            });
        }
        log.info("Total Emails size " + emailsList.size());
        try {
            emailService.sendEmail(emailsList, request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }

        emailsList.stream().forEach(email -> employeeMoodService.save(EmployeeMood.of(email, MoodType.NO_RESPONSE.name(), request.getTemplateId())));

        return new ResponseEntity<>("Email has been sent to the recipients.", HttpStatus.OK);
    }
}
