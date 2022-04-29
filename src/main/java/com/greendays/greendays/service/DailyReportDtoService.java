package com.greendays.greendays.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greendays.greendays.mapper.DailyReportEntityToDailyReportDtoMapper;
import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.paging.*;
import com.greendays.greendays.repository.DailyReportRepository;
import com.greendays.greendays.service.page.DailyReportComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DailyReportDtoService {
    private static final Comparator<DailyReportDto> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private final DailyReportRepository dailyReportRepository;


    public PageArray getGarbageDtosArray(PagingRequest pagingRequest) {
        pagingRequest.setColumns(Stream.of("id", "date", "routeNumber", "uat", "quantity", "destination", "driverName", "carNumber", "problems", "incidentType", "observations", "clientType", "garbageCode", "garbageName", "routeSheet", "weightTalon")
                .map(Column::new)
                .collect(Collectors.toList()));
        //TODO: replace call to actual db
        Page<DailyReportDto> pageOfDailyReportsDtos = getMockDataDtos(pagingRequest);

        PageArray pageArray = new PageArray();
        pageArray.setRecordsFiltered(pageOfDailyReportsDtos.getRecordsFiltered());
        pageArray.setRecordsTotal(pageOfDailyReportsDtos.getRecordsTotal());
        pageArray.setDraw(pageOfDailyReportsDtos.getDraw());
        pageArray.setData(pageOfDailyReportsDtos.getData()
                .stream()
                .map(this::toStringList)
                .collect(Collectors.toList()));
        return pageArray;
    }

    private List<String> toStringList(DailyReportDto dailyReportDto) {
        return Arrays.asList(
                String.valueOf(dailyReportDto.getId()),
                dailyReportDto.getDate().toString(),
                String.valueOf(dailyReportDto.getRouteNumber()),
                dailyReportDto.getUat(),
                String.valueOf(dailyReportDto.getQuantity()),
                dailyReportDto.getDestination(),
                dailyReportDto.getDriverName(),
                dailyReportDto.getCarNumber(),
                dailyReportDto.getProblems(),
                dailyReportDto.getIncidentType(),
                dailyReportDto.getObservations(),
                dailyReportDto.getClientType(),
                dailyReportDto.getGarbageCode(),
                dailyReportDto.getGarbageName(),
                dailyReportDto.getRouteSheet(),
                dailyReportDto.getWeightTalon()
        );
    }

    public Page<DailyReportDto> getDailyReportsDtos(PagingRequest pagingRequest) {
        List<DailyReportDto> reports = dailyReportRepository.findAll().stream()
                .map(DailyReportEntityToDailyReportDtoMapper::mapEntityToDto)
                .collect(Collectors.toList());

        return getPage(reports, pagingRequest);
    }

    public Page<DailyReportDto> getMockDataDtos(PagingRequest pagingRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<DailyReportDto> dailyReportDtos = objectMapper.readValue(getClass().getClassLoader()
                            .getResourceAsStream("MOCK_DATA.json"),
                    new TypeReference<List<DailyReportDto>>() {
                    });
            return getPage(dailyReportDtos, pagingRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Page<DailyReportDto> getPage(List<DailyReportDto> dailyReportDtos, PagingRequest pagingRequest) {
        List<DailyReportDto> filtered = dailyReportDtos.stream()
                .sorted(sortGarbageDtos(pagingRequest))
                .filter(filterDailyReportsDtos(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = dailyReportDtos.stream()
                .filter(filterDailyReportsDtos(pagingRequest))
                .count();

        Page<DailyReportDto> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<DailyReportDto> filterDailyReportsDtos(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return garbageDto -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return dailyReportDto -> String.valueOf(dailyReportDto.getId())
                .toLowerCase()
                .contains(value) || String.valueOf(dailyReportDto.getDate())
                .toLowerCase()
                .contains(value) || dailyReportDto.getUat()
                .toLowerCase()
                .contains(value) || dailyReportDto.getGarbageCode()
                .toLowerCase()
                .contains(value) || dailyReportDto.getGarbageName()
                .toLowerCase()
                .contains(value) || dailyReportDto.getClientType()
                .toLowerCase()
                .contains(value)  || dailyReportDto.getDriverName()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<DailyReportDto> sortGarbageDtos(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<DailyReportDto> comparator = DailyReportComparator.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            System.out.println(e);
        }

        return EMPTY_COMPARATOR;
    }
}
