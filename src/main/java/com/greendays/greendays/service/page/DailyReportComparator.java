package com.greendays.greendays.service.page;

import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.paging.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Date;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class DailyReportComparator {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<DailyReportDto>> map = new HashMap<>();

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(DailyReportDto::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(DailyReportDto::getId)
                .reversed());

        map.put(new Key("date", Direction.asc), Comparator.comparing(DailyReportDto::getDate));
        map.put(new Key("date", Direction.desc), Comparator.comparing(DailyReportDto::getDate)
                .reversed());

        map.put(new Key("routeNumber", Direction.asc), Comparator.comparing(DailyReportDto::getRouteNumber));
        map.put(new Key("routeNumber", Direction.desc), Comparator.comparing(DailyReportDto::getRouteNumber)
                .reversed());



        map.put(new Key("uat", Direction.asc), Comparator.comparing(DailyReportDto::getUat));
        map.put(new Key("uat", Direction.desc), Comparator.comparing(DailyReportDto::getUat)
                .reversed());

        map.put(new Key("quantity", Direction.asc), Comparator.comparing(DailyReportDto::getQuantity));
        map.put(new Key("quantity", Direction.desc), Comparator.comparing(DailyReportDto::getQuantity)
                .reversed());

        map.put(new Key("destination", Direction.asc), Comparator.comparing(DailyReportDto::getDestination));
        map.put(new Key("destination", Direction.desc), Comparator.comparing(DailyReportDto::getDestination)
                .reversed());



        map.put(new Key("driverName", Direction.asc), Comparator.comparing(DailyReportDto::getDriverName));
        map.put(new Key("driverName", Direction.desc), Comparator.comparing(DailyReportDto::getDriverName)
                .reversed());

        map.put(new Key("carNumber", Direction.asc), Comparator.comparing(DailyReportDto::getCarNumber));
        map.put(new Key("carNumber", Direction.desc), Comparator.comparing(DailyReportDto::getCarNumber)
                .reversed());

        map.put(new Key("clientType", Direction.asc), Comparator.comparing(DailyReportDto::getClientType));
        map.put(new Key("clientType", Direction.desc), Comparator.comparing(DailyReportDto::getClientType)
                .reversed());



        map.put(new Key("garbageCode", Direction.asc), Comparator.comparing(DailyReportDto::getGarbageCode));
        map.put(new Key("garbageCode", Direction.desc), Comparator.comparing(DailyReportDto::getGarbageCode)
                .reversed());

        map.put(new Key("garbageName", Direction.asc), Comparator.comparing(DailyReportDto::getGarbageName));
        map.put(new Key("garbageName", Direction.desc), Comparator.comparing(DailyReportDto::getGarbageName)
                .reversed());
    }

    public static Comparator<DailyReportDto> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private DailyReportComparator() {
    }
}