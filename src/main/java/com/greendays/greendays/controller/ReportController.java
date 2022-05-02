package com.greendays.greendays.controller;

import com.greendays.greendays.model.dal.DailyReport;
import com.greendays.greendays.report.PdfReportGenerator;
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

@Controller
@RequiredArgsConstructor
public class ReportController {

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> trimestrialPdfReport() {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=monthlyReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new PdfReportGenerator().generateTrimestrialPdfReport()));
    }


    @RequestMapping(value = "/pdfMonthlyReport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> monthlyReport() {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=monthlyReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new PdfReportGenerator().generateMonthlyPdfReport()));
    }
}
