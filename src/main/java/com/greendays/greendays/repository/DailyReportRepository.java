package com.greendays.greendays.repository;

import com.greendays.greendays.model.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DailyReportRepository extends JpaRepository<DailyReport,Long> {
}
