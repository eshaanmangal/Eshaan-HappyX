package com.xebia.happix.service;

import com.xebia.happix.client.ZohoClientService;
import com.xebia.happix.model.EmployeeMetaData;
import com.xebia.happix.model.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ZohoClientService zohoClientService;

    @Value(value = "${zoho.token}")
    private String token;


    @Override
    public Set<EmployeeResponse> getAllStaff() {
        int sIndex=0;
        int recLimit=200;
        Set<EmployeeResponse> staffResponseList =new HashSet<EmployeeResponse>();
        Boolean isMoreData=true;
        while(isMoreData) {
            List<EmployeeMetaData> staffMetaDataList = zohoClientService.getAllEmployee(token,sIndex,recLimit);
            System.out.println(staffMetaDataList.size());
            if(staffMetaDataList.size() ==1 ){
                EmployeeMetaData staffMetaData=staffMetaDataList.get(0);
                if(staffMetaData.getEmployeeID() ==null ){
                    isMoreData=false;
                    continue;
                }
            }
            if(staffMetaDataList.size() > 0 ) {
                staffResponseList.addAll(EmployeeResponse.of(staffMetaDataList));
                sIndex=sIndex+recLimit;
            }else {
                isMoreData=false;
            }
        }
        return staffResponseList;

    }
}
