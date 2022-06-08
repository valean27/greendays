package com.greendays.greendays.service.scheduled;

import com.greendays.greendays.service.PdfReportGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class MonthlyReportScheduler {
    private final PdfReportGenerator pdfReportGenerator;

    //TESTINGPURPOSE
//    @Scheduled(fixedDelay = 1000)
    @Scheduled(cron = "0 0 7 3 * *")
    public void generateAndStoreMonthlyReport() {
        LocalDate localDate = LocalDate.now();
        log.info("Generare raport lunar pentru {}", localDate);
        Date date = Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        ByteArrayInputStream byteArrayInputStream = pdfReportGenerator.generateMonthlyPdfReports(date);
        File file = new File("src/main/resources/arhiva/"+ localDate + "_greendays-raport-lunar-activitate.pdf");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            IOUtils.copy(byteArrayInputStream, new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
