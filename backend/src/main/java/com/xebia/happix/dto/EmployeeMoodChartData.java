package com.xebia.happix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeMoodChartData {
    private Long totalCount;
    private List<MoodStatistics> moodStatistics;

}
