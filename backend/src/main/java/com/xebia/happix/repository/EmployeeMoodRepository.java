package com.xebia.happix.repository;

import com.xebia.happix.dto.MoodStatistics;
import com.xebia.happix.model.EmployeeMood;
import feign.Param;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmployeeMoodRepository extends PagingAndSortingRepository<EmployeeMood, Long> {
    Page<EmployeeMood> findByEmail(String email, Pageable paging);

    List<EmployeeMood> findByEmailAndMoodTypeIsNull(String email, Pageable paging);

    List<EmployeeMood> findByTemplateID(String templateID, Pageable paging);

    List<EmployeeMood> findByCreatedDateBetweenAndEmailAndTemplateID(LocalDateTime startDateTime, LocalDateTime endDateTime, String Email, String TemplateID);

    List<EmployeeMood> findByEmailOrderByCreatedDateDesc(String email);

    @Query(nativeQuery = true ,value = "SELECT * from EMPLOYEE_MOOD ORDER BY CREATED_DATE DESC LIMIT 1")
    EmployeeMood abc();

    Page<EmployeeMood> findAllByCreatedDateBetween(LocalDateTime createdDateStart, LocalDateTime createdDateEnd, Pageable paging);

    @Query("SELECT " +
            "    new com.xebia.happix.dto.MoodStatistics(empmood.moodType, COUNT(empmood)) " +
            "FROM " +
            "    EmployeeMood empmood " +
            "GROUP BY " +
            "    empmood.moodType")
    List<MoodStatistics> groupBy();

    @Query("SELECT " +
            "    new com.xebia.happix.dto.MoodStatistics(empmood.moodType, COUNT(empmood)) " +
            "FROM " +
            "    EmployeeMood empmood " +
            "WHERE empmood.templateID = :TemplateID AND ( empmood.createdDate >= :startDate AND empmood.createdDate <= :endDate ) " +
            "GROUP BY " +
            "    empmood.moodType")
    List<MoodStatistics> groupByDate(@Param("TemplateID") String TemplateID, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
