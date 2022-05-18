package com.greendays.greendays.model.totals;

import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.dto.Destination;
import com.greendays.greendays.model.dto.Trimester;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class TrimestrialReportData {
    public static final String STATIE_TRANSFER_BLAJ = "Statia de Transfer Blaj";
    private List<DailyReportDto> dailyReportsThisTrimester;
    private final Trimester trimester;

    private List<DailyReportDto> getReportsByUatAndClientType(String uat, String clientType) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat) && dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .collect(Collectors.toList());
    }

    private List<DailyReportDto> getReportsByUatClientTypeAndGarbageName(String uat, String clientType, String garbageName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat) && dailyReportDto.getClientType().equalsIgnoreCase(clientType) && dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getTransferStationSubmitted() {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(Destination.TRANSFER_STATION.getDestinationName()))
                .collect(Collectors.toList());
    }

    private List<DailyReportDto> getReportsByClientTypeAndGarbageName(String clientType, String garbageName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName) && dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .collect(Collectors.toList());
    }


    private List<DailyReportDto> getReportsByClientTypeAndGarbageNameAndDestination(String clientType, String garbageName, Destination destination) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName) && dailyReportDto.getClientType().equalsIgnoreCase(clientType) && dailyReportDto.getDestination().equalsIgnoreCase(destination.getDestinationName()))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> getGarbageReportsByClientTypeGarbageNameAndDestination(String clientType, String garbageName, String destination) {
        return dailyReportsThisTrimester.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getClientType().equalsIgnoreCase(clientType) && report.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }


    public List<DailyReportDto> getGarbageReportsByUatClientTypeGarbageNameAndDestination(String uat, String clientType, String garbageName, String destination) {
        return dailyReportsThisTrimester.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getUat().equalsIgnoreCase(uat) && report.getClientType().equalsIgnoreCase(clientType) && report.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    public Double getTotalForUatClientTypeGarbageNameAndDestination(String uat, String clientType, String garbageName, String destination) {
        return getGarbageReportsByUatClientTypeGarbageNameAndDestination(uat, clientType, garbageName, destination).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }

    public Double getTotalForClientTypeGarbageNameAndDestination(String clientType, String garbageName, String destination) {
        return getGarbageReportsByClientTypeGarbageNameAndDestination(clientType, garbageName, destination).stream()
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }


    public Double getTotalByUatAndClientType(String uat, String clientType) {
        return getReportsByUatAndClientType(uat, clientType)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }

    public Double getTotalTransferStationByGarbageNameAndClientType(String garbageName, String clientType) {
        return getTransferStationSubmitted()
                .stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName) && dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }

    public Double getTotalByUatClientTypeAndGarbageName(String uat, String clientType, String garbageName) {
        return getReportsByUatClientTypeAndGarbageName(uat, clientType, garbageName)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }

    public Double getTotalByUatAndGarbageName(String uat, String garbageName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat) && dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }


    public Double getTotalByClientTypeAndGarbageName(String clientType, String garbageName) {
        return getReportsByClientTypeAndGarbageName(clientType, garbageName)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }


    public Double getTotalByClientTypeAndGarbageNameRural(String clientType, String garbageName) {
        return getReportsByClientTypeAndGarbageName(clientType, garbageName)
                .stream().filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }


    public Double getTotalByClientTypeAndGarbageNameRuralAndDestination(String clientType, String garbageName, Destination destination) {
        return getReportsByClientTypeAndGarbageNameAndDestination(clientType, garbageName, destination)
                .stream().filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(0D, Double::sum);
    }
}
