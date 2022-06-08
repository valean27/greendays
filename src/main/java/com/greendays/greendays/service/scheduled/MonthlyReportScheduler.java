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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        File folder = new File("src/main/resources/arhiva/" + localDate.getMonthValue() + "-" + localDate.getYear());
        if (!folder.exists()) {
            folder.mkdir();
        }
        File file = new File("src/main/resources/arhiva/" + folder.getName()+ "/" + localDate + "_greendays-raport-lunar-activitate.pdf");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: NEED TO PACKAGE ALSO THE FOAIE TRANSPORT AND ALL THOSE
        try {
            IOUtils.copy(byteArrayInputStream, new FileOutputStream(file));
            String sourceFile = folder.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream("src/main/resources/zipuri/" + localDate + "-fisiere-raport-lunar-activitate.zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
