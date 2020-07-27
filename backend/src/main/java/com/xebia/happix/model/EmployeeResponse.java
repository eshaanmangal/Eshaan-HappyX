package com.xebia.happix.model;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class EmployeeResponse {

    private String staffID;

    private String staffName;

    private String staffEmailId;

    private String departmentName;

    private String location;

    public static Set<EmployeeResponse> of(List<EmployeeMetaData> staffMetaDataList) {
        Set<EmployeeResponse> staffResponseList=new HashSet<>();
        for (EmployeeMetaData staffMetaData : staffMetaDataList) {
            EmployeeResponse staffResponse = new EmployeeResponse();
            staffResponse.setStaffEmailId(staffMetaData.getXebiaEmailId());
            staffResponse.setDepartmentName(staffMetaData.getDepartment());
            staffResponse.setStaffID(staffMetaData.getEmployeeID());
            staffResponse.setStaffName(staffMetaData.getFullName());
            staffResponse.setLocation(staffMetaData.getLocation());
            staffResponseList.add(staffResponse);

        }
        return staffResponseList;
    }
}

