package com.greendays.greendays.service;

import com.greendays.greendays.model.Garbage;
import com.greendays.greendays.repository.GarbageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GarbageService {

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
}
