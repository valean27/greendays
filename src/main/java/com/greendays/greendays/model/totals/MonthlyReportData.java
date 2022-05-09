package com.greendays.greendays.model.totals;

import com.greendays.greendays.model.dto.DailyReportDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class MonthlyReportData {
    private List<DailyReportDto> dailyReportsFromThisMonth;
    private int monthNumber;
    private int year;

    public List<DailyReportDto> getRezidualGarbageReportsByUat(String uat) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase("rezidual") && report.getUat().equalsIgnoreCase(uat))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getRezidualGarbageReportsByUatAndClientType(String uat, String clientType) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase("rezidual") && report.getUat().equalsIgnoreCase(uat) && report.getClientType().equalsIgnoreCase(clientType))
                .collect(Collectors.toList());
    }


    public List<DailyReportDto> getGarbageReportsByUatClientTypeAndGarbageName(String uat, String clientType, String garbageName) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getUat().equalsIgnoreCase(uat) && report.getClientType().equalsIgnoreCase(clientType))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getGarbageReportsByUatAndGarbageName(String uat, String garbageName) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getUat().equalsIgnoreCase(uat))
                .collect(Collectors.toList());
    }

    public Double getTotalForUatAndGarbageName(String uat, String garbageName) {
        return getGarbageReportsByUatAndGarbageName(uat, garbageName).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }

    public Double getTotalForUatClientTypeAndGarbageName(String uat, String clientType, String garbageName) {
        return getGarbageReportsByUatClientTypeAndGarbageName(uat, clientType, garbageName).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }



}
