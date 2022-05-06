package com.greendays.greendays.repository;

import com.greendays.greendays.model.dal.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    List<DailyReport> findAllByDateBetween(Date startDate, Date endDate);

    @Query("SELECT dailyReport FROM DailyReport dailyReport WHERE (MONTH(DATE) = :firstMonth OR  MONTH(DATE) = :secondMonth OR  MONTH(DATE) = :thirdMonth) AND YEAR(DATE) = :year")
    List<DailyReport> findAllForQuarter(int firstMonth, int secondMonth, int thirdMonth, int year);
}
