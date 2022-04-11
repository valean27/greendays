package com.greendays.greendays.repository;

import com.greendays.greendays.model.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport,Long> {
    List<DailyReport> findAllByDateBetween(Date startDate, Date endDate);
 }
