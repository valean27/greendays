package com.greendays.greendays.service;

import com.greendays.greendays.model.dal.DailyReport;
import com.greendays.greendays.model.dto.Trimester;
import com.greendays.greendays.repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyReportService {

    private final DailyReportRepository dailyReportRepository;

    public String postDailyReport(DailyReport report) {
        try {
            dailyReportRepository.save(report);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Raport Salvat!";
    }

    public List<DailyReport> getAllReportsFromThisMonth() {
        return dailyReportRepository.findAllByDateBetween(Date.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())), Date.valueOf(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())));
    }

    public DailyReport getReportById(String id) {
        return dailyReportRepository.getById(Long.parseLong(id));
    }

    public List<DailyReport> getAllReportsForQuarter(Trimester trimester, int year) {
        return dailyReportRepository.findAllForQuarter(trimester.getMonth1(), trimester.getMonth2(), trimester.getMonth3(), year);
    }

    public List<DailyReport> getAllReportsOfMonth(int month, int year) {
        return dailyReportRepository.findAllForMonth(month, year);
    }

}
