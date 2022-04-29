package com.greendays.greendays.service;

import com.greendays.greendays.model.dal.Garbage;
import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.repository.DailyReportRepository;
import com.greendays.greendays.repository.GarbageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GarbageService {
    private static final Comparator<DailyReportDto> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private final GarbageRepository garbageRepository;
    private final DailyReportRepository dailyReportRepository;

    public List<Garbage> getGarbages() {
        return garbageRepository.findAll();
    }

    public void insertGarbage(String garbageCode, String garbageName) {
        Garbage garbage = new Garbage();
        garbage.setGarbageCode(garbageCode);
        garbage.setGarbageName(garbageName);
        garbageRepository.save(garbage);
    }
}
