package com.greendays.greendays.report;

import com.greendays.greendays.model.DailyReport;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class GeneratePdfReport {

   private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class.getName());
    public static ByteArrayInputStream monthlyReport(List<DailyReport> dailyReports) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{3, 3, 3,3});

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

            hcell = new PdfPCell(new Phrase("Statie Transfer -Destinatie finala", headFont));
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
                cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(report.getDestination()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
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
}