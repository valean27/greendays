package com.greendays.greendays.mapper;

import com.greendays.greendays.model.dal.DailyReport;
import com.greendays.greendays.model.dto.DailyReportDto;

import java.sql.Date;

public class DailyReportEntityToDailyReportDtoMapper {

    public static DailyReportDto mapEntityToDto(DailyReport dailyReport) {
        DailyReportDto dailyReportDto = new DailyReportDto();
        dailyReportDto.setId(dailyReport.getId());
        dailyReportDto.setDate(dailyReport.getDate());
        dailyReportDto.setCarNumber(dailyReport.getCarNumber());
        dailyReportDto.setGarbageCode(dailyReport.getGarbage().getGarbageCode());
        dailyReportDto.setGarbageName(dailyReport.getGarbage().getGarbageName());
        dailyReportDto.setClientType(dailyReport.getClient().getClientType());
        dailyReportDto.setDestination(dailyReport.getDestination());
        dailyReportDto.setObservations(dailyReport.getIncident().getObservations());
        dailyReportDto.setIncidentType(dailyReport.getIncident().getIncidentType());
        dailyReportDto.setDriverName(dailyReport.getDriverName());
        dailyReportDto.setRouteNumber(dailyReport.getRouteNumber());
        dailyReportDto.setUat(dailyReport.getUat());
        dailyReportDto.setQuantity(dailyReport.getQuantity());
        dailyReportDto.setRouteSheet(dailyReport.getRouteSheet());
        dailyReportDto.setWeightTalon(dailyReport.getWeightTalon());

        return dailyReportDto;
    }
}
