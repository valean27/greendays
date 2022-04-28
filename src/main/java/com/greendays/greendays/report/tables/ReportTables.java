package com.greendays.greendays.report.tables;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class ReportTables {
    public static final String FONT = "/Users/bogdan.filip/Desktop/greendays/src/main/resources/FreeSans.ttf";

    public void createFirstReportTable1(Document document, Font headFont, Font mainFont) throws DocumentException {
        PdfPTable firstRow = new PdfPTable(2);
        firstRow.setWidthPercentage(80);
        firstRow.setWidths(new int[]{4, 10});
        firstRow.setKeepTogether(true);

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
    }

    public void createCentralisingTable2(Document document, Font headFont, Font mainFont) {
        //todo: add according values
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable mainTable = getCentralisingTable(4);

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


        } catch (Exception e) {

        }

    }

    public void createUrbanEnvTable3(Document document, Font headFont, Font mainFont) {
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Categorie deșeu", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


            addCellToTable(mainTable, "Blaj", mainFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
            addCellToTable(mainTable, " ", mainFont);
            addCellToTable(mainTable, " ", mainFont);

            document.add(mainTable);
        } catch (Exception e) {

        }


    }


    public void createRuralEnvTable4(Document document, Font headFont, Font mainFont) {
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


            multiplyCodeForGarbageType(garbageType -> {
                addCellToTable(mainTable, garbageType, mainFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
                addTableToTable(mainTable, 2, mainFont, asList(" ", " "));
                addTableToTable(mainTable, 2, mainFont, asList(" ", " "));
            }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă", "Total 1"));


            //todo:add total rezidual rural

            document.add(mainTable);

        } catch (Exception e) {
            File file = new File("/file.txt");

            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(e.getMessage());
                fileWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void createRecyclableUrbanTable5(Document document, Font headFont, Font mainFont) {

        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable mainTable = new PdfPTable(5);
            mainTable.setWidthPercentage(80);
            mainTable.setWidths(new int[]{6, 4, 4, 4, 4});
            mainTable.setKeepTogether(true);


            addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Categorie deșeu", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);

            addCellToTable(mainTable, "Blaj", headFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"));
            addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   "," ", " ", " ", "   "));
            addTableToTable(mainTable, 4, mainFont, asList(" ", " ", " ", "  "));

            addCellToTable(mainTable, "Total Urban", headFont);
            addTableToTable(mainTable, 2, headFont, asList("Casnic", "Non-casnic"));
            addTableToTable(mainTable, 8, headFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   "," ", " ", " ", "   "));
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "  "," ", " ", " ", "  "));

            //TODO: Aici e o problema de spatiere

            document.add(mainTable);

        } catch (Exception e) {
            e.printStackTrace();
            File file = new File("/file.txt");

            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(e.getMessage());
                fileWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void createRecyclableRuralTable6(Document document, Font headFont, Font mainFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(5);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{6, 4, 4, 4, 4});
        mainTable.setKeepTogether(true);


        addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);
        addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


        multiplyCodeForGarbageType(uat-> {
                addCellToTable(mainTable, uat, headFont);
        addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"));
        addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
        addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   "," ", " ", " ", "   "));
        addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "  "," ", " ", " ", "  "));

        },asList("Crăciunelu de Jos","Bucerdea Grânoasă","Șona","Jidvei","Cergău","Cenade","Cetatea de Baltă","Roșia de Secaș","Sâncel","Valea Lungă"));

        addCellToTable(mainTable, "Total Rural", headFont);
        addTableToTable(mainTable, 2, headFont, asList("Casnic", "Non-casnic"));
        addTableToTable(mainTable, 8, headFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
        addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   "," ", " ", " ", "   "));
        addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "  "," ", " ", " ", "  "));
        document.add(mainTable);
        //TODO: ADD TOTAL RECYCLABLE RURAL
    }


//    public void  createRecyclableUrbanTable(Document document){
//        try {
//        document.add(new Paragraph("\n"));
//        document.add(new Paragraph("\n"));
//
//            PdfPTable mainTable = getCentralisingTable(5);
//
//            Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD, "UTF-8");
//            Font mainFont = FontFactory.getFont(FontFactory.HELVETICA, "UTF-8");
//
//            addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
//            addCellToTable(mainTable, "Tip beneficiar", headFont);
//            addCellToTable(mainTable, "Categorie deșeu", headFont);
//            addCellToTable(mainTable, "Cantitate (tone)", headFont);
//            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);
//
////            addCellToTable(mainTable, "Blaj", headFont);
//
//            document.add(mainTable);
//
//
//        }catch (Exception e){
//
//        }
//
//
//    }


    private PdfPTable getCentralisingTable(int colNumber) throws DocumentException {
        PdfPTable mainTable = new PdfPTable(colNumber);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{4, 4, 4, 4});
        mainTable.setKeepTogether(true);
        return mainTable;
    }

    public void addCellToTable(PdfPTable pdfPTable, String phrase, Font font) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase, font));
        pdfPTable.addCell(pdfPCell);
    }

    public void addCellToTable(PdfPTable pdfPTable, String phrase, Font font, int minHeight) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase, font));
        pdfPTable.addCell(pdfPCell);
        pdfPCell.setMinimumHeight(20f);
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
