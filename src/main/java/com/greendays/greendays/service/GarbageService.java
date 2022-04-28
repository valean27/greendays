package com.greendays.greendays.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greendays.greendays.model.dal.Garbage;
import com.greendays.greendays.model.dto.GarbageDto;
import com.greendays.greendays.model.paging.*;
import com.greendays.greendays.repository.GarbageRepository;
import com.greendays.greendays.service.page.GarbageComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GarbageService {
    private static final Comparator<GarbageDto> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private final GarbageRepository garbageRepository;

    public List<Garbage> getGarbages(){
        return garbageRepository.findAll();
    }

    public void insertGarbage(String garbageCode, String garbageName){
        Garbage garbage = new Garbage();
        garbage.setGarbageCode(garbageCode);
        garbage.setGarbageName(garbageName);
        garbageRepository.save(garbage);
    }

    public PageArray getGarbageDtosArray(PagingRequest pagingRequest) {
        pagingRequest.setColumns(Stream.of("id","name", "garbageCode")
                .map(Column::new)
                .collect(Collectors.toList()));
        Page<GarbageDto> garbageDtoPage = getGarbageDtos(pagingRequest);

        PageArray pageArray = new PageArray();
        pageArray.setRecordsFiltered(garbageDtoPage.getRecordsFiltered());
        pageArray.setRecordsTotal(garbageDtoPage.getRecordsTotal());
        pageArray.setDraw(garbageDtoPage.getDraw());
        pageArray.setData(garbageDtoPage.getData()
                .stream()
                .map(this::toStringList)
                .collect(Collectors.toList()));
        return pageArray;
    }

    private List<String> toStringList(GarbageDto garbageDto) {
        return Arrays.asList(String.valueOf(garbageDto.getId()), garbageDto.getGarbageName(),garbageDto.getGarbageCode());
    }

    public Page<GarbageDto> getGarbageDtos(PagingRequest pagingRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<GarbageDto> garbageDtos = objectMapper.readValue(getClass().getClassLoader()
                            .getResourceAsStream("garbage.json"),
                    new TypeReference<List<GarbageDto>>() {
                    });

            return getPage(garbageDtos, pagingRequest);

        } catch (IOException e) {
            System.out.println(e);
        }

        return new Page<>();
    }

    private Page<GarbageDto> getPage(List<GarbageDto> garbageDtos, PagingRequest pagingRequest) {
        List<GarbageDto> filtered = garbageDtos.stream()
                .sorted(sortGarbageDtos(pagingRequest))
                .filter(filterGarbageDtos(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = garbageDtos.stream()
                .filter(filterGarbageDtos(pagingRequest))
                .count();

        Page<GarbageDto> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<GarbageDto> filterGarbageDtos(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return garbageDto -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return garbageDto -> garbageDto.getGarbageName()
                .toLowerCase()
                .contains(value)
                || garbageDto.getGarbageCode()
                .toLowerCase()
                .contains(value)
                || String.valueOf(garbageDto.getId())
                .toLowerCase()
                .contains(value);
    }

    private Comparator<GarbageDto> sortGarbageDtos(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<GarbageDto> comparator = GarbageComparator.getComparator(column.getData(), order.getDir());
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
