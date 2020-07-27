package com.xebia.happix.service;

import com.xebia.happix.model.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public interface EmployeeService {

    public Set<EmployeeResponse> getAllStaff();
}
