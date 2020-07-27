package com.xebia.happix.dto;

import com.xebia.happix.model.MoodType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoodStatistics {
    private String moodType;
    private Long count;
}
