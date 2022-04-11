package com.greendays.greendays.controller;

import com.greendays.greendays.model.DailyReport;
import com.greendays.greendays.report.GeneratePdfReport;
import com.greendays.greendays.service.DailyReportService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final DailyReportService dailyReportService;

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport() {

        List<DailyReport> dailyReports = dailyReportService.getAllReportsFromThisMonth();

//        ByteArrayInputStream bis = GeneratePdfReport.monthlyReport(dailyReports);
//        ByteArrayInputStream bis = GeneratePdfReport.trimestrialRaportPlataAdiSalubris();
        ByteArrayInputStream bis = GeneratePdfReport.trimestrialRaportPlataUAT();

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=monthlyReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
