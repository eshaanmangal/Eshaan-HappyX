package com.xebia.happix.application.service;

import com.xebia.happix.model.EmployeeResponse;
import com.xebia.happix.service.EmployeeService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MasterDataService {
    @Autowired
    private EmployeeService employeeService;
    private boolean beanInitialized;
    private Set<EmployeeResponse> staffResponseSet = new ConcurrentSkipListSet<>();
    private Boolean isMapInitialised = false;

    private Map<String, EmployeeResponse> mappedRecord;

    private Set<String> locationList = new HashSet<>();

    private Set<String> departmentList = new HashSet<>();

    private Map<String, Set<EmployeeResponse>> locationWiseRecord = new HashMap<>();

    private Map<String, Set<EmployeeResponse>> departmentWiseRecord =new HashMap<>();
    private List<EmployeeResponse> employeeResponses;

    public boolean isBeanInitialized() {
        return this.beanInitialized;
    }

    public void setBeanInitializedtoFalse() {
        this.beanInitialized = false;
    }

    public synchronized void initialize() throws InterruptedException, ExecutionException {
        log.debug("******************* Initializing master data starts *******************");

        this.beanInitialized = false;

        StopWatch watch = new StopWatch();
        watch.start();

        staffResponseSet = employeeService.getAllStaff();
        staffResponseSet.stream().forEach(employeeResponse -> {
            locationList.add(employeeResponse.getLocation());
            departmentList.add(employeeResponse.getDepartmentName());
            if (locationWiseRecord.containsKey(employeeResponse.getLocation())) {
                Set<EmployeeResponse> employeeResponses = locationWiseRecord.get(employeeResponse.getLocation());
                employeeResponses.add(employeeResponse);
                locationWiseRecord.put(employeeResponse.getLocation(), employeeResponses);
            } else {
                Set<EmployeeResponse> employeeResponses = new HashSet<>();
                employeeResponses.add(employeeResponse);
                locationWiseRecord.put(employeeResponse.getLocation(), employeeResponses);
            }

            if (departmentWiseRecord.containsKey(employeeResponse.getDepartmentName())) {
                Set<EmployeeResponse> employeeResponses = departmentWiseRecord.get(employeeResponse.getDepartmentName());
                employeeResponses.add(employeeResponse);
                departmentWiseRecord.put(employeeResponse.getDepartmentName(), employeeResponses);
            } else {
                Set<EmployeeResponse> employeeResponses = new HashSet<>();
                employeeResponses.add(employeeResponse);
                departmentWiseRecord.put(employeeResponse.getDepartmentName(), employeeResponses);
            }
        });
        this.beanInitialized = true;
        watch.split();
        watch.stop();
        log.debug("************ Master Data initialization completed in: " + watch.toSplitString()
                + " *******************");
    }

    public Set<EmployeeResponse> getAllStaff() {
        return staffResponseSet;
    }

    public EmployeeResponse getStaffDetailsByEmail(String email) {
        if (!isMapInitialised) {
            mappedRecord = new HashMap<>();
            staffResponseSet.stream().forEach(emp -> mappedRecord.put(emp.getStaffEmailId(), emp));
        }
        return mappedRecord.get(email);
    }

    public Set<String> getAllLocations() {
        return locationList;
    }

    public Set<String> getAllDepartment() {
        return departmentList;
    }

    public Set<EmployeeResponse> getEmployeeListByLoctionWise(String location) {
        return locationWiseRecord.get(location);
    }

    public Map<String, Set<EmployeeResponse>> getEmployeeListByLoctionWise() {
        return locationWiseRecord;
    }

    public Set<EmployeeResponse> getEmployeeListByDepartmentWise(String department) {
        return departmentWiseRecord.get(department);
    }

    public Map<String, Set<EmployeeResponse>> getEmployeeListByDepartmentWise() {
        return departmentWiseRecord;
    }

}
