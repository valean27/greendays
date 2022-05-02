package com.greendays.greendays.report.tables;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.security.core.parameters.P;

import javax.print.Doc;

import static java.util.Arrays.asList;

public class MonthlyReportTables extends TablesCreatorHelper {

    public void createHeaderTable1(Document document, Font mainFont, Font headFont, String referenceDate) throws DocumentException {
        PdfPTable firstRow = new PdfPTable(2);
        firstRow.setWidthPercentage(80);
        firstRow.setWidths(new int[]{4, 10});
        firstRow.setKeepTogether(true);

        PdfPCell hcell = new PdfPCell(new Phrase("Baza legală", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        firstRow.addCell(hcell);

        addTableToTable(firstRow, 2, headFont, asList("Contract de delegare 159/23.08.2018", "- Caietul de sarcini, Cap. 10 – Monitorizarea activităților de către Autoritatea Contractantă. \n - Contract de delegare, Art. 13 – Monitorizarea contractului"));

        addCellToTable(firstRow, "Perioada de referință", headFont);
        addCellToTable(firstRow, referenceDate, headFont);
        addCellToTable(firstRow, "Lotul de operare", headFont);
        addCellToTable(firstRow, "- 4 Blaj", headFont);
        addCellToTable(firstRow, "Operator delegat", headFont);
        addCellToTable(firstRow, "- Asocierea SC GREENDAYS SRL / GREENDAYS 2-SOLUÇOES AMBIENTAIS SA", headFont);

        document.add(firstRow);
    }

    public void createUrbanResidualGarbageTable2(Document document, Font mainFont, Font headFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(3);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{4, 5, 1});
        mainTable.setKeepTogether(true);

        addCellToTableAlignedCenter(mainTable, "UAT", headFont);
        addStrangeTableToTable(mainTable, headFont);
        addCellToTable(mainTable, "Obs.", headFont);

        addCellToTableAlignedCenter(mainTable, "Blaj", mainFont);
        addTableToTable(mainTable, 3, 3, mainFont, asList(" ", " ", " "));
        addCellToTable(mainTable, " ", mainFont);

        document.add(mainTable);
    }


    public void createRuralResidualGarbageTable3(Document document, Font mainFont, Font headFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(3);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{4, 5, 1});
        mainTable.setKeepTogether(true);

        addCellToTableAlignedCenter(mainTable, "UAT", headFont);
        addStrangeTableToTable(mainTable, headFont);
        addCellToTable(mainTable, "Obs.", headFont);

        multiplyCodeForGarbageType(uat -> {
            addCellToTableAlignedCenter(mainTable, uat, mainFont);
            addTableToTable(mainTable, 3, 3, mainFont, asList(" ", " ", " "));
            addCellToTable(mainTable, " ", mainFont);
        }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));

        document.add(mainTable);
    }

    public void createTransferStationSubmittedGarbageTable4(Document document, Font mainFont, Font headFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{4, 3, 3, 2});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire stație transfer", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Cantitate", headFont);


        addCellToTableAlignedCenter(mainTable, "Blaj", headFont);
        addTableToTable(mainTable, 5, mainFont, asList("Rezidual", "Hartie, carton", "Plastic", "Metal", "Sticla"));
        addTableToTable(mainTable, 10, mainFont, asList("casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici"));
        addTableToTable(mainTable, 10, mainFont, asList(" ", " ", " ", " ", " ", " ", " ", " ", " ", " "));

        document.add(mainTable);
        //TODO: add total
    }

    public void createDepositSubmittedGarbageTable5(Document document, Font mainFont, Font headFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{4, 3, 3, 2});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire depozit", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Cantitate", headFont);


        addCellToTableAlignedCenter(mainTable, "Galda de Jos", headFont);
        addTableToTable(mainTable, 5, mainFont, asList("Rezidual", "Hartie, carton", "Plastic", "Metal", "Sticla"));
        addTableToTable(mainTable, 10, mainFont, asList("casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici"));
        addTableToTable(mainTable, 10, mainFont, asList(" ", " ", " ", " ", " ", " ", " ", " ", " ", " "));

        document.add(mainTable);
        //TODO: add total
    }

    private void addStrangeTableToTable(PdfPTable mainTable, Font font) {
        //set border
        PdfPTable pdfPTable = new PdfPTable(1);
        PdfPCell pdfPCell1 = new PdfPCell(new Phrase("Cantități deșeuri munincipale colectate\n           (tone)", font));
        PdfPTable innerTable = new PdfPTable(3);
        mainTable.setKeepTogether(true);
        addCellToTable(innerTable, "Total, d.c.", font);
        addCellToTable(innerTable, "Casnici", font);
        addCellToTable(innerTable, "Non Casnici", font);

        pdfPTable.addCell(pdfPCell1);
        pdfPTable.addCell(innerTable);
        mainTable.addCell(pdfPTable);
    }
}
