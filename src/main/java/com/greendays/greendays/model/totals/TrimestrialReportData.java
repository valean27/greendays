package com.greendays.greendays.model.totals;

import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.dto.Destination;
import com.greendays.greendays.model.dto.Trimester;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
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
        System.out.println(dailyReportsThisTrimester);
        return dailyReportsThisTrimester.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getClientType().equalsIgnoreCase(clientType) && report.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }


    public List<DailyReportDto> getGarbageReportsByUatClientTypeGarbageNameAndDestination(String uat, String clientType, String garbageName, String destination) {
        return dailyReportsThisTrimester.stream()
                .filter(report -> report.getGarbageName().equalsIgnoreCase(garbageName) && report.getUat().equalsIgnoreCase(uat) && report.getClientType().equalsIgnoreCase(clientType) && report.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
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


    public BigDecimal getTotalByUatAndClientType(String uat, String clientType) {
        return getReportsByUatAndClientType(uat, clientType)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalTransferStationByGarbageNameAndClientType(String garbageName, String clientType) {
        return getTransferStationSubmitted()
                .stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName) && dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalByUatClientTypeAndGarbageName(String uat, String clientType, String garbageName) {
        return getReportsByUatClientTypeAndGarbageName(uat, clientType, garbageName)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalByUatAndGarbageName(String uat, String garbageName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat) && dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalByUatGarbageNameAndDestination(String uat, String garbageName, Destination destination) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat) && dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(destination.getDestinationName()))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalByClientTypeAndGarbageName(String clientType, String garbageName) {
        return getReportsByClientTypeAndGarbageName(clientType, garbageName)
                .stream().map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }


    public BigDecimal getTotalByClientTypeAndGarbageNameRural(String clientType, String garbageName) {
        return getReportsByClientTypeAndGarbageName(clientType, garbageName)
                .stream().filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }


    public BigDecimal getTotalByClientTypeAndGarbageNameRuralAndDestination(String clientType, String garbageName, Destination destination) {
        return getReportsByClientTypeAndGarbageNameAndDestination(clientType, garbageName, destination)
                .stream().filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public Object getTotalByClientTypeDestinationAndRecyclableOrRezidual(String clientType, Destination transferStation) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> isRecyclable(dailyReportDto))
                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(transferStation.getDestinationName()))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    private boolean isRecyclable(DailyReportDto dailyReportDto) {
        return dailyReportDto.getGarbageName().equalsIgnoreCase("Hârtie și carton") ||
                dailyReportDto.getGarbageName().equalsIgnoreCase("Plastic") ||
                dailyReportDto.getGarbageName().equalsIgnoreCase("Metal") ||
                dailyReportDto.getGarbageName().equalsIgnoreCase("Sticlă");
    }

    public String getTotalOtherTypes() {
        return dailyReportsThisTrimester.stream()
                .filter(
                        dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase("Voluminoase") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Periculoase") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Abandonate") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Deșeuri din construcții și demolări")
                ).map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public String getTotalOtherTypesUrban() {
        return dailyReportsThisTrimester.stream()
                .filter(
                        dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase("Voluminoase") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Periculoase") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Abandonate") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Deșeuri din construcții și demolări")
                ).filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public String getTotalOtherTypesRural() {
        return dailyReportsThisTrimester.stream()
                .filter(
                        dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase("Voluminoase") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Periculoase") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Abandonate") ||
                                dailyReportDto.getGarbageName().equalsIgnoreCase("Deșeuri din construcții și demolări")
                ).filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public String getTotalForTrimester() {
        return dailyReportsThisTrimester.stream()
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public BigDecimal getTotalByUatGarbageNameAndClientType(String uat, String garbageName, String clientType) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat))
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public String getTotalRezidualRural() {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase("rezidual"))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public String getTotalRecyclableByUat(String uat) {
        return dailyReportsThisTrimester.stream()
                .filter(this::isRecyclable)
                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }


    public String getTotalRecyclableRural() {
        return dailyReportsThisTrimester.stream()
                .filter(this::isRecyclable)
                .filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public String getTotalRecyclableByClientType(String clientType) {
        return dailyReportsThisTrimester.stream()
                .filter(this::isRecyclable)
                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public String getTotalRecyclable() {
        return dailyReportsThisTrimester.stream()
                .filter(this::isRecyclable)
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add)
                .toString();
    }

    public BigDecimal getTotalRuralByClientTypeAndGarbageName(String clientType, String garbageName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .map(dailyReportDto -> dailyReportDto.getQuantity())
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public Object getTotalByGarbageNameAndDestination(String uat, String garbageName) {
        return null;
    }

    public BigDecimal getTotalRuralByGarbageName(String garbageName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .filter(dailyReportDto -> !dailyReportDto.getUat().equalsIgnoreCase("Blaj"))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public BigDecimal getTotalByGarbageNameClientTypeAndDestination(String clientType, String garbageName, String destinationName) {
        return dailyReportsThisTrimester.stream()
                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
                .filter(dailyReportDto -> dailyReportDto.getDestination().equalsIgnoreCase(destinationName))
                .map(DailyReportDto::getQuantity)
                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
    }

    public void convertToTons() {
        dailyReportsThisTrimester.forEach(dailyReportDto -> dailyReportDto.setQuantity(dailyReportDto.getQuantity().divide(new BigDecimal(1000))));
    }
//
//    public BigDecimal getTotalByUatGarbageNameAndClientType(String uat, String garbageName, String clientType) {
//        return dailyReportsThisTrimester.stream()
//                .filter(dailyReportDto -> dailyReportDto.getUat().equalsIgnoreCase(uat))
//                .filter(dailyReportDto -> dailyReportDto.getGarbageName().equalsIgnoreCase(garbageName))
//                .filter(dailyReportDto -> dailyReportDto.getClientType().equalsIgnoreCase(clientType))
//                .map(DailyReportDto::getQuantity)
//                .reduce(BigDecimal.valueOf(0D), BigDecimal::add);
//    }
}
