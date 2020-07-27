package com.xebia.happix.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmployeeMood {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator="HAPPYX_SEQ")
    @SequenceGenerator(name = "HAPPYX_SEQ" , initialValue = 1000)
    @Column(name = "ID")
    @CsvBindByPosition(position = 0)
    private long id;

    @Column(name = "EMAIL")
    @NotNull
    @CsvBindByName(column = "Email")
    @CsvBindByPosition(position = 1)
    private String email;

    @Column(name = "MOOD_TYPE")
    @CsvBindByName(column = "MoodType")
    @CsvBindByPosition(position = 2)
    private String moodType;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Transient
    @CsvBindByName(column = "Created_Date")
    @CsvBindByPosition(position = 3)
    private String createdDateString;

    @Transient
    @CsvBindByName(column = "Updated_Date")
    @CsvBindByPosition(position = 4)
    private String updatedDateString;   
    
    @Column(name = "TemplateID")
    @CsvBindByPosition(position = 5)
    private String templateID;

    public static EmployeeMood of(String email){
        EmployeeMood employeeMood=new EmployeeMood();
        employeeMood.setEmail(email);
        return employeeMood;
    }

    public static EmployeeMood of(String email, String moodType, String templateID){
        EmployeeMood employeeMood=new EmployeeMood();
        employeeMood.setTemplateID(templateID);
        employeeMood.setEmail(email);
        employeeMood.setMoodType(moodType);
        return employeeMood;
    }

    public static EmployeeMood of(String email, String moodType){
        EmployeeMood employeeMood=new EmployeeMood();
        employeeMood.setEmail(email);
        employeeMood.setMoodType(moodType);
        return employeeMood;
    }
}
