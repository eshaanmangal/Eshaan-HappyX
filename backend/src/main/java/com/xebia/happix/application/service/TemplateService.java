//package com.xebia.happix.application.service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TemplateService {
//
//    private List<String> templateidList = new ArrayList<>();
//    private Map <String,List<String>> templatedIdMap = new HashMap<>();
//
//
//    public List<String> getAllTemplateId(){
//        templateidList.add("1");
//        templateidList.add("2");
//        return templateidList;
//    }
//
//    public Map<String,List<String>> getTemplateIDwithMoods(){
//        List <String> templ2 = new ArrayList<>();
//        templ2.add("Highly Motivated");
//        templ2.add("Motivated");
//        templ2.add("Clueless");
//        templ2.add("No");
//
//        List<String> templ1 = new ArrayList<>();
//        templ1.add("Confident");
//        templ1.add("Anxious");
//        templ1.add("Confused");
//        templ1.add("Upset");
//        templatedIdMap.put("1",templ1);
//        templatedIdMap.put("2",templ2);
//
//        return templatedIdMap;
//    }
//}