package com.greendays.greendays.service.scheduled;

import com.greendays.greendays.model.dto.Trimester;
import com.greendays.greendays.service.PdfReportGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TrimestrialReportScheduler {
    private final PdfReportGenerator pdfReportGenerator;

    @Scheduled(cron = "0 0 7 3 * *")
    public void generateAndStoreTrimestrialReport() {
        LocalDate localDate = LocalDate.now();
        if (localDate.getMonthValue() % 3 == 0) {
            pdfReportGenerator.generateTrimestrialPdfReport(Trimester.valueOf(localDate.getMonthValue() / 3));
        }

    }
}
