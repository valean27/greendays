package com.greendays.greendays.controller;

import com.greendays.greendays.model.dal.DailyReport;
import com.greendays.greendays.model.dto.Trimester;
import com.greendays.greendays.service.PdfReportGenerator;
import com.greendays.greendays.service.DailyReportService;
import com.greendays.greendays.service.Quarter;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportController {

    @Autowired
    DailyReportService dailyReportService;

    @Autowired
    PdfReportGenerator pdfReportGenerator;

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> trimestrialPdfReport(@RequestParam String quarter) {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=monthlyReport.pdf");

        Trimester trimester = Trimester.valueOf(Integer.parseInt(quarter));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfReportGenerator.generateTrimestrialPdfReport(trimester)));
    }


    @RequestMapping(value = "/pdfMonthlyReport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> monthlyReport(@RequestParam String data, @RequestParam String data2) throws ParseException {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=monthlyReport.pdf");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedCurrentDate = sdf.parse(data);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfReportGenerator.generateMonthlyPdfReports(convertedCurrentDate)));
    }


    @ResponseBody
    @RequestMapping(value = "/getForQuarter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailyReport> quarter() {
       return dailyReportService.getAllReportsForQuarter(Trimester.I, 2022);
    }

}
