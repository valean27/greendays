package com.greendays.greendays.service.scheduled;

import com.greendays.greendays.mapper.DailyReportEntityToDailyReportDtoMapper;
import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.repository.DailyReportRepository;
import com.greendays.greendays.service.DailyReportService;
import com.greendays.greendays.service.PdfReportGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@Slf4j
@RequiredArgsConstructor
public class MonthlyReportScheduler {
    private final PdfReportGenerator pdfReportGenerator;
    private final DailyReportService dailyReportService;

    //TESTINGPURPOSE
//    @Scheduled(initialDelay = 30000, fixedDelay = 60000)
    @Scheduled(cron = "0 0 7 3 * *")
    public void generateAndStoreMonthlyReport() {

        LocalDate localDate = LocalDate.now();
        List<DailyReportDto> dailyReportDtos = dailyReportService.getAllReportsOfMonth(localDate.getMonthValue(), localDate.getYear()).stream()
                .map(DailyReportEntityToDailyReportDtoMapper::mapEntityToDto)
                .collect(Collectors.toList());


        log.info("Generare raport lunar pentru {}", localDate);
        Date date = Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        ByteArrayInputStream byteArrayInputStream = pdfReportGenerator.generateMonthlyPdfReports(date);
        File folder = new File("src/main/resources/arhiva/" + localDate.getMonthValue() + "-" + localDate.getYear());
        if (!folder.exists()) {
            folder.mkdir();
        }

        dailyReportDtos.stream()
                .filter(dailyReportDto -> !isBlank(dailyReportDto.getWeightTalon()))
                .forEach(weightTalons -> {
                    try {
                        Files.copy(Paths.get(weightTalons.getWeightTalon()), Paths.get(folder.getAbsolutePath() + "/" + StringUtils.remove(weightTalons.getWeightTalon(),"upload-dir/")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        dailyReportDtos.stream()
                .filter(dailyReportDto -> !isBlank(dailyReportDto.getRouteSheet()))
                .forEach(routeSheet -> {
                    try {
                        Files.copy(Paths.get(routeSheet.getRouteSheet()), Paths.get(folder.getAbsolutePath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


        File file = new File("src/main/resources/arhiva/" + folder.getName() + "/" + localDate + "_greendays-raport-lunar-activitate.pdf");

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
