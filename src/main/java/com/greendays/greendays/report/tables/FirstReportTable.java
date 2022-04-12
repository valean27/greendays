package com.greendays.greendays.report.tables;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class FirstReportTable {
    public void createFirstReportTable(Document document) throws DocumentException {
        PdfPTable firstRow = new PdfPTable(2);
        firstRow.setWidthPercentage(80);
        firstRow.setWidths(new int[]{4, 10});
        firstRow.setKeepTogether(true);

        Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD, "UTF-8");

        PdfPCell hcell = new PdfPCell(new Phrase("Baza legală", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        firstRow.addCell(hcell);

        addTableToTable(firstRow, 2, headFont, asList("- Caietul de sarcini, Cap. 10 – Monitorizarea activităților de către Autoritatea Contractantă și 18.16. Cerinte privind raportarea – perioada de operare", "- Contract de delegare, Art. 13 – Monitorizarea contractului"));

        addCellToTable(firstRow, "Perioada de referință", headFont);
        addCellToTable(firstRow, "- Decembrie 2021 - Februarie 2022", headFont);
        addCellToTable(firstRow, "Lotul de operare", headFont);
        addCellToTable(firstRow, "- 4 Blaj", headFont);
        addCellToTable(firstRow, "Operator delegat", headFont);
        addCellToTable(firstRow, "- Asocierea SC GREENDAYS SRL / GREENDAYS 2-SOLUÇOES AMBIENTAIS SA", headFont);

        document.add(firstRow);

        createCentralisingTable(document);


    }

    public void createCentralisingTable(Document document) {
        //todo: add according values
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable mainTable = getCentralisingTable();

            Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD, "UTF-8");
            Font mainFont = FontFactory.getFont(FontFactory.HELVETICA, "UTF-8");

            addCellToTable(mainTable, "Categorie deșeu", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);

            multiplyCodeForGarbageType(garbageType -> {
                addCellToTable(mainTable, garbageType, mainFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
                addTableToTable(mainTable, 2, mainFont, asList(" ", " "));
                addTableToTable(mainTable, 2, mainFont, asList(" ", " "));
            }, asList("Rezidual", "Hârtie și carton", "Plastic", "Metal", "Sticlă", "Total 1"));

            addCellToTable(mainTable, "Voluminoase", mainFont);
            addCellToTable(mainTable, " ", mainFont);
            addCellToTable(mainTable, " ", mainFont);
            addCellToTable(mainTable, "  ", mainFont);

            multiplyCodeForGarbageType(garbageType -> {
                addCellToTable(mainTable, garbageType, mainFont);
                addCellToTable(mainTable, " ", mainFont);
                addCellToTable(mainTable, " ", mainFont);
                addCellToTable(mainTable, "  ", mainFont);
            }, asList("Voluminoase", "Periculoase", "Abandonate", "Deșeuri din construcții și demolări"));

            document.add(mainTable);

           createUrbanEnvTable(document);

        } catch (Exception e) {

        }

    }

    public void createUrbanEnvTable(Document document){
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable mainTable = getCentralisingTable();

            Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD, "UTF-8");
            Font mainFont = FontFactory.getFont(FontFactory.HELVETICA, "UTF-8");

            addCellToTable(mainTable, "Categorie deșeu", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


            addCellToTable(mainTable,"Blaj", mainFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
            addCellToTable(mainTable," ",mainFont);
            addCellToTable(mainTable," ",mainFont);

            document.add(mainTable);

            createRuralEnvTable(document);
        }catch (Exception e){

        }


    }


    public void createRuralEnvTable(Document document){

        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable mainTable = getCentralisingTable();

            Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD, "UTF-8");
            Font mainFont = FontFactory.getFont(FontFactory.HELVETICA, "UTF-8");

            addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


            multiplyCodeForGarbageType(garbageType -> {
                addCellToTable(mainTable, garbageType, mainFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
                addTableToTable(mainTable, 2, mainFont, asList(" ", " "));
                addTableToTable(mainTable, 2, mainFont, asList(" ", " "));
            }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă","Total 1"));


            //todo:add total rezidual rural

            document.add(mainTable);

        }catch (Exception e){

        }

    }

    private PdfPTable getCentralisingTable() throws DocumentException {
        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{4, 4, 4, 4});
        mainTable.setKeepTogether(true);
        return mainTable;
    }

    public void addCellToTable(PdfPTable pdfPTable, String phrase, Font font) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase, font));
        pdfPTable.addCell(pdfPCell);
    }

    public void addTableToTable(PdfPTable pdfPTable, int rowNumber, Font font, java.util.List<String> args) {
        PdfPTable innerTable = new PdfPTable(1);
        for (int i = 0; i < rowNumber; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(args.get(i), font));
            innerTable.addCell(cell);
        }
        PdfPCell innerCell = new PdfPCell(innerTable);
        innerCell.setBorder(0);
        pdfPTable.addCell(innerCell);
    }

    public void multiplyCodeForGarbageType(Consumer<String> code, java.util.List<String> args) {
        for (String arg : args) {
            code.accept(arg);
        }
    }
}
