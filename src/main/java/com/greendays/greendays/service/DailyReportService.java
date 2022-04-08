package com.greendays.greendays.service;

import com.greendays.greendays.model.DailyReport;
import com.greendays.greendays.repository.ClientRepository;
import com.greendays.greendays.repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
