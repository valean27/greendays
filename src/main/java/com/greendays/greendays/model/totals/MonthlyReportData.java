package com.greendays.greendays.model.totals;

import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.dto.Destination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class MonthlyReportData {
    public static final String STATIE_TRANSFER_BLAJ = "Statia de Transfer Blaj";
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


    public List<DailyReportDto> getGarbageReportsByUatClientTypeGarbageNameAndDestination(String uat, String clientType, String garbageName, String destination) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getUat().equalsIgnoreCase(uat) && report.getClientType().equalsIgnoreCase(clientType) && report.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getGarbageReportsByClientTypeGarbageNameAndDestination(String clientType, String garbageName, String destination) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getClientType().equalsIgnoreCase(clientType) && report.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }


    public List<DailyReportDto> getGarbageReportsByUatAndGarbageName(String uat, String garbageName) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getUat().equalsIgnoreCase(uat))
                .collect(Collectors.toList());
    }

    //CMID = depozit Galda () (TMB = deseuri menajere)
    //Statie transfer blaj () -> aici ajung deseuri reziduale si reciclabile
    //

    public List<DailyReportDto> getRecyclableReportsByUatAndClientType(String uat, String clientType) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getUat().equalsIgnoreCase(uat) && clientType.equalsIgnoreCase(report.getClientType()) &&
                        (report.getGarbageName().equalsIgnoreCase("Sticlă") ||
                                report.getGarbageName().equalsIgnoreCase("Hârtie și carton") ||
                                report.getGarbageName().equalsIgnoreCase("Plastic") ||
                                report.getGarbageName().equalsIgnoreCase("Metal")))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getRecyclableReportsByUat(String uat) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getUat().equalsIgnoreCase(uat) &&
                        (report.getGarbageName().equalsIgnoreCase("Sticlă") ||
                                report.getGarbageName().equalsIgnoreCase("Hârtie și carton") ||
                                report.getGarbageName().equalsIgnoreCase("Plastic") ||
                                report.getGarbageName().equalsIgnoreCase("Metal")))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getTransferStationSubmitted() {
        return dailyReportsFromThisMonth.stream()
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(Destination.TRANSFER_STATION.getDestinationName()))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getCMIDGaldaSubmitted() {
        return dailyReportsFromThisMonth.stream()
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(Destination.CMID_GALDA.getDestinationName()))
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalTransferStationByGarbageNameAndClientType(String garbageName, String clientType) {
        return getTransferStationSubmitted()
                .stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName) && dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalCMIDGaldaSubmitedByGarbageNameAndClientType(String garbageName, String clientType) {
        return getCMIDGaldaSubmitted()
                .stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName) && dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }


    public BigDecimal getTotalRecyclableByUatAndClientType(String uat, String clientType) {
        return getRecyclableReportsByUatAndClientType(uat, clientType)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);

    }

    public BigDecimal getTotalRecyclableByUat(String uat) {
        return getRecyclableReportsByUat(uat)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }


    public BigDecimal getTotalForUatAndGarbageName(String uat, String garbageName) {
        return getGarbageReportsByUatAndGarbageName(uat, garbageName).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalForUatClientTypeAndGarbageName(String uat, String clientType, String garbageName) {
        return getGarbageReportsByUatClientTypeAndGarbageName(uat, clientType, garbageName).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalForUatClientTypeGarbageNameAndDestination(String uat, String clientType, String garbageName, String destination) {
        return getGarbageReportsByUatClientTypeGarbageNameAndDestination(uat, clientType, garbageName, destination).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalForClientTypeGarbageNameAndDestination(String clientType, String garbageName, String destination) {
        return getGarbageReportsByClientTypeGarbageNameAndDestination(clientType, garbageName, destination).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalForClientTypeRecyclableAndDestination(String clientType, String garbageName, String destination) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase("Sticlă") ||
                        report.getGarbageName().equalsIgnoreCase("Hârtie și carton") ||
                        report.getGarbageName().equalsIgnoreCase("Plastic") ||
                        report.getGarbageName().equalsIgnoreCase("Metal"))
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(destination))
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalForClientTypeResidualAndDestination(String clientType, String garbageName, String destination) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase("rezidual"))
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(destination))
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }


    public BigDecimal getTotalForGarbageTypeAndDestination(String garbageName, String destination) {
        return dailyReportsFromThisMonth.stream().filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(destination) && dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalByGarbageName(String garbageName) {
        return dailyReportsFromThisMonth.stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalRecyclable() {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> (report.getGarbageName().equalsIgnoreCase("Sticlă") ||
                        report.getGarbageName().equalsIgnoreCase("Hârtie și carton") ||
                        report.getGarbageName().equalsIgnoreCase("Plastic") ||
                        report.getGarbageName().equalsIgnoreCase("Metal")))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalRecyclableByDestination(Destination destination) {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> (report.getGarbageName().equalsIgnoreCase("Sticlă") ||
                        report.getGarbageName().equalsIgnoreCase("Hârtie și carton") ||
                        report.getGarbageName().equalsIgnoreCase("Plastic") ||
                        report.getGarbageName().equalsIgnoreCase("Metal")) && report.getDestination().equalsIgnoreCase(destination.getDestinationName()))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalOtherTypes() {
        return dailyReportsFromThisMonth.stream()
                .filter(report -> (report.getGarbageName().equalsIgnoreCase("Voluminoase") ||
                        report.getGarbageName().equalsIgnoreCase("Periculoase") ||
                        report.getGarbageName().equalsIgnoreCase("Abandonate") ||
                        report.getGarbageName().equalsIgnoreCase("Deșeuri din construcții și demolări")))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotal() {
        return dailyReportsFromThisMonth.stream().map(DailyReportDto::getQuantity).reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

}
