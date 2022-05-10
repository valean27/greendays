package com.greendays.greendays.model.totals;

import com.greendays.greendays.model.dto.DailyReportDto;
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

    private List<DailyReportDto> getReportsByClientTypeAndGarbageName(String clientType, String garbageName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName) && dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .collect(Collectors.toList());
    }


    public Double getTotalByUatAndClientType(String uat, String clientType) {
        return getReportsByUatAndClientType(uat, clientType)
                .stream().map(DailyReportDto::getQuantity)
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
}
