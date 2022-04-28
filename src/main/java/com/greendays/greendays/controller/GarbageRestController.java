package com.greendays.greendays.controller;

import com.greendays.greendays.model.dto.GarbageDto;
import com.greendays.greendays.model.paging.Page;
import com.greendays.greendays.model.paging.PageArray;
import com.greendays.greendays.model.paging.PagingRequest;
import com.greendays.greendays.service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: This will be the controller that will populate datatable with everything, but now we just test with garbage
@RestController
@RequestMapping("garbage")
public class GarbageRestController {

    private final GarbageService garbageService;

    @Autowired
    public GarbageRestController(GarbageService garbageService) {
        this.garbageService = garbageService;
    }

    @PostMapping
    public Page<GarbageDto> list(@RequestBody PagingRequest pagingRequest) {
        return garbageService.getGarbageDtos(pagingRequest);
    }

    @PostMapping("/array")
    public PageArray array(@RequestBody PagingRequest pagingRequest) {
        return garbageService.getGarbageDtosArray(pagingRequest);
    }
}