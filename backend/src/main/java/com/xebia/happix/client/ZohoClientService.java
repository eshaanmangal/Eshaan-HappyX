package com.xebia.happix.client;

import com.xebia.happix.model.EmployeeMetaData;
import feign.Param;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="ZohoClientService", url="https://people.zoho.com/people/api/forms")
public interface ZohoClientService {
    @GetMapping(value = "/P_EmployeeView/records")
    List<EmployeeMetaData> getAllEmployee(@RequestParam (value="authtoken") String authtoken,
                                          @RequestParam(value="sIndex") int sIndex,
                                          @RequestParam(value="recLimit") int recLimit);
}
