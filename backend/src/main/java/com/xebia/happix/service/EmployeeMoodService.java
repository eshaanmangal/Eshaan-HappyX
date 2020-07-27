package com.xebia.happix.service;

import com.xebia.happix.dto.EmployeeMoodChartData;
import com.xebia.happix.dto.MoodStatistics;
import com.xebia.happix.model.EmployeeMood;
import com.xebia.happix.repository.EmployeeMoodRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class EmployeeMoodService {

    @Autowired
    private EmployeeMoodRepository employeeMoodRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;

    public EmployeeMood save(EmployeeMood employeeMood) {

        LocalDateTime now = LocalDateTime.now();
        List<EmployeeMood> employeeMoods = employeeMoodRepository.findByCreatedDateBetweenAndEmailAndTemplateID(
                now.minusDays(2), now,
                employeeMood.getEmail(), employeeMood.getTemplateID());

        if (employeeMoods.isEmpty()) {
            return employeeMoodRepository.save(employeeMood);
        } else {
            EmployeeMood mode = employeeMoods.get(0);
            mode.setMoodType(employeeMood.getMoodType());
            return employeeMoodRepository.save(mode);
        }
    }

    public EmployeeMood saveFromEmail(EmployeeMood employeeMood) {
        return employeeMoodRepository.save(employeeMood);
    }

    public Page<EmployeeMood> getAllEmployees(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdDate"));

        return employeeMoodRepository.findAll(paging);

    }


    public Page<EmployeeMood> getAllEmployeesByEmail(Integer pageNo, Integer pageSize, String email) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdDate"));

        return employeeMoodRepository.findByEmail(email, paging);

    }

    public Page<EmployeeMood> getAllEmployeeBetween(LocalDateTime start, LocalDateTime end, Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdDate"));

        return employeeMoodRepository.findAllByCreatedDateBetween(start, end, paging);
    }

    public EmployeeMood save(String email, String moodType, String templateID) {
        return save(EmployeeMood.of(email, moodType, templateID));
    }

//    public EmployeeMoodChartData getEmployeeMoodStatistics() {
//        Long totalCount = employeeMoodRepository.count();
//        List<MoodStatistics> moodStatistics = employeeMoodRepository.groupBy();
//        Set<String> allMoodType = emailTemplateService.findAll().stream()
//                .map(emailTemplate -> emailTemplate.getMoods())
//                .flatMap(Set::stream)
//                .collect(Collectors.toSet());
//        List<String> allProcessMoodType = moodStatistics.stream()
//                .map(m -> m.getMoodType())
//                .collect(Collectors.toList());
//        allMoodType.forEach(m -> {
//            if (!allProcessMoodType.contains(m)) {
//                moodStatistics.add(new MoodStatistics(m, new Long(0)));
//            }
//        });
//
//        return new EmployeeMoodChartData(totalCount, moodStatistics);
//    }

    public EmployeeMoodChartData getEmployeeMoodStatistics(){

        EmployeeMood employeeMood = employeeMoodRepository.abc();

        if(employeeMood == null){
//            List<MoodStatistics> moodStatisticsList = new LinkedList<>();
//            Set<String> allMoodType = emailTemplateService.findOne("MailTemplate").get().getMoods();
//            for(String data:allMoodType){
//                MoodStatistics obj = new MoodStatistics(data, (long) 0);
//                moodStatisticsList.add(obj);
//            }
            return new EmployeeMoodChartData((long) 0, Collections.emptyList());
        }

        LocalDate startDate = (employeeMood.getCreatedDate()).toLocalDate();
        String templateId = employeeMood.getTemplateID();


        Set<String> allMoodType = emailTemplateService.findOne(templateId).get().getMoods();

        List<MoodStatistics> moodStatistics = employeeMoodRepository.groupByDate(templateId,
                startDate.atTime(LocalTime.MIDNIGHT), startDate.atTime(LocalTime.MAX));

        AtomicReference<Long> totalCount = new AtomicReference<>(new Long(0));
        //totalCount = Long.valueOf(moodStatistics.size());

        Set<String> allProcessMoodType = moodStatistics.stream()
                .map(m -> {
                    totalCount.updateAndGet(v -> v + m.getCount());
                    return m.getMoodType();
                })
                .collect(Collectors.toSet());

        allMoodType.forEach(m -> {
            if (!allProcessMoodType.contains(m)) {
                moodStatistics.add(new MoodStatistics(m, new Long(0)));
            }
        });

        return new EmployeeMoodChartData(totalCount.get(), moodStatistics);
    }


    public TreeMap<LocalDate, List<MoodStatistics>> getEmployeeMoodStatisticsbyDate(String templateId, LocalDate startDate, LocalDate endDate) {
        //Map<LocalDate, List<MoodStatistics>> dataByDays = new HashMap<>();
        TreeMap<LocalDate,List<MoodStatistics>> dataByDays = new TreeMap<>();
        Set<String> allMoodType = emailTemplateService.findOne(templateId).get().getMoods();

        while (startDate.isBefore(endDate) || startDate.equals(endDate)) {
            List<MoodStatistics> moodStatistics = employeeMoodRepository.groupByDate(templateId,
                    startDate.atTime(LocalTime.MIDNIGHT), startDate.atTime(LocalTime.MAX));
            Set<String> allProcessMoodType = moodStatistics.stream()
                    .map(m -> m.getMoodType())
                    .collect(Collectors.toSet());

            allMoodType.forEach(m -> {
                if (!allProcessMoodType.contains(m)) {
                    moodStatistics.add(new MoodStatistics(m, new Long(0)));
                }
            });

            JSONArray jsArray = new JSONArray();
            for(MoodStatistics data:moodStatistics){
                JSONObject obj=new JSONObject();  
                obj.put(data.getMoodType(),data.getCount());
                jsArray.add(obj);
            }
            dataByDays.put(startDate, jsArray);
            //moodStatistics.clear();
            startDate = startDate.plusDays(1);
        }
        return dataByDays;
    }

}