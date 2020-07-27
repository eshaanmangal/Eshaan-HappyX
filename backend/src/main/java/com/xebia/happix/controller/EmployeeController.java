package com.xebia.happix.controller;

import com.xebia.happix.application.service.MasterDataService;
import com.xebia.happix.model.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private MasterDataService masterDataService;

    @GetMapping("/employee")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<EmployeeResponse>> getStaffList(@RequestParam(value = "name", required = false) final String name) {
        if (name != null) {
            return ResponseEntity.ok().body(masterDataService.getAllStaff().stream()
                    .filter(employee -> employee.getStaffName().contains(name))
                    .collect(Collectors.toSet()));
        } else {
            return ResponseEntity.ok().body(masterDataService.getAllStaff());
        }
    }

    @GetMapping("/company/locations")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<String>> getAllLocations() {
        return ResponseEntity.ok().body(masterDataService.getAllLocations());
    }

    @GetMapping("/company/departments")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<String>> getAllDepartments() {
        return ResponseEntity.ok().body(masterDataService.getAllDepartment());
    }

    @GetMapping("/employee/locations")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStaffListLocationWise(@RequestParam(value = "location", required = false) final String location) {
        if (location != null) {
            return ResponseEntity.ok().body(masterDataService.getEmployeeListByLoctionWise(location));
        } else {
            return ResponseEntity.ok().body(masterDataService.getEmployeeListByLoctionWise());
        }
    }
    @GetMapping("/employee/departments")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStaffListDepartmentWise(@RequestParam(value = "department", required = false) final String department) {
        if (department != null) {
            return ResponseEntity.ok().body(masterDataService.getEmployeeListByDepartmentWise(department));
        } else {
            return ResponseEntity.ok().body(masterDataService.getEmployeeListByDepartmentWise());
        }
    }

    @GetMapping("/company/locOrDept")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<String>> getAllLocationsAndDepartment() {
        Set<String> allLocationsAndDepartment=masterDataService.getAllLocations();
        allLocationsAndDepartment.addAll(masterDataService.getAllDepartment());
        return ResponseEntity.ok().body(allLocationsAndDepartment);
    }
}
