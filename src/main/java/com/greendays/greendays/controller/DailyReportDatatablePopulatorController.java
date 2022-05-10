package com.greendays.greendays.controller;

import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.paging.Page;
import com.greendays.greendays.model.paging.PagingRequest;
import com.greendays.greendays.service.DailyReportDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: This will be the controller that will populate datatable with everything, but now we just test with garbage
@RestController
@RequestMapping("garbage")
public class DailyReportDatatablePopulatorController {

    private final DailyReportDtoService dailyReportDtoService;

    @Autowired
    public DailyReportDatatablePopulatorController(DailyReportDtoService dailyReportDtoService) {
        this.dailyReportDtoService = dailyReportDtoService;
    }

    //TEST PURPOSE
    @PostMapping
    public Page<DailyReportDto> list(@RequestBody PagingRequest pagingRequest) {
//        return garbageService.getMockDataDtos(pagingRequest); //this is the mock service
        return dailyReportDtoService.getDailyReportsDtos(pagingRequest);
    }

}