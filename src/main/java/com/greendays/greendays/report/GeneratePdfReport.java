package com.greendays.greendays.report;

import com.greendays.greendays.model.dal.DailyReport;
import com.greendays.greendays.report.tables.ReportTables;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class GeneratePdfReport {

    private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class.getName());
    public static final String FONT = "src/main/resources/FreeSans.ttf";
    public static final String FONT_BOLD = "src/main/resources/FreeSansBold.ttf";

    public static ByteArrayInputStream monthlyReport(List<DailyReport> dailyReports) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.addTitle("Raport Lunar");
        document.addHeader("header", "Raport Lunar");
        try {

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{3, 3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Categorie Deseu", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Tip Beneficiar", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Cantitate (tone)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Statie Transfer - Destinatie finala", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (DailyReport report : dailyReports) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(report.getGarbage().getGarbageName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);


                cell = new PdfPCell(new Phrase(report.getClient().getClientType()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(report.getQuantity().toString()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(report.getDestination()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream headerReportTable() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.addTitle("Raport Lunar");
        document.addHeader("header", "Raport Lunar");

        BaseFont bf = null;
        Font font = null;
        Font boldFont = null;
        try {
            bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont boldBaseFont = BaseFont.createFont(FONT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            font = new Font(bf);
            boldFont = new Font(boldBaseFont);
            PdfWriter.getInstance(document, out);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.open();
        ReportTables reportTables = new ReportTables();
        try {
            reportTables.createFirstReportTable1(document, boldFont, font);
            reportTables.createCentralisingTable2(document, boldFont, font);
            reportTables.createUrbanEnvTable3(document, boldFont, font);
            reportTables.createRuralEnvTable4(document, boldFont, font);
            reportTables.createRecyclableUrbanTable5(document, boldFont, font);
            reportTables.createRecyclableRuralTable6(document, boldFont, font);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

    //TODO vezi ce se baga in raportul asta
    public static ByteArrayInputStream trimestrialRaportPlataAdiSalubris() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.addTitle("Raport Lunar");
        document.addHeader("header", "Raport Lunar");
        try {

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{3, 3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.TIMES);
//todo Trebuie sa aflam un font care suporta diacritice, vezi BasicLatin
            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Cifra de afaceri (lei)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Taxa de administrare (lei)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Document de plata nr.", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Data platii", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public static ByteArrayInputStream trimestrialRaportPlataUAT() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.addTitle("Raport Lunar");
        document.addHeader("header", "Raport Lunar");
        try {

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(95);
            table.setWidths(new float[]{2, 3, 10, 3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.TIMES);

            PdfPTable outsideTable = new PdfPTable(1);
            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Cifra de afaceri(lei)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            outsideTable.addCell(hcell);


            PdfPTable insideTable = new PdfPTable(4);
//            insideTable.setWidthPercentage();
            insideTable.setWidths(new float[]{4, 5, 4, 4.6f});

            hcell = new PdfPCell(new Phrase("TOTAL d.c.", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            insideTable.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Persoane fizice (Tarif 1,respectiv Tarif 2,după caz)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            insideTable.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Persoane juridice (Tarif 3)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            insideTable.addCell(hcell);


            hcell = new PdfPCell(new Phrase("Servicii ocazionale(Tarif 4 – 8)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            insideTable.addCell(hcell);


//            ----------------------------------------------------

            PdfPCell myCell = new PdfPCell(insideTable);
            myCell.setBorderWidth(0);
            outsideTable.addCell(myCell);


            hcell = new PdfPCell(new Phrase("Nr. Crt", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("UAT", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(hcell);


            hcell = new PdfPCell(outsideTable);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Redeventa (lei)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Document plata nr. (lei)", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(hcell);


            hcell = new PdfPCell(new Phrase("Data platii", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(hcell);


            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}