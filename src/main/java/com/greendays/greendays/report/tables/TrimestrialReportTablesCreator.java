package com.greendays.greendays.report.tables;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.*;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class TrimestrialReportTablesCreator extends TablesCreatorHelper {
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
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   ", " ", " ", " ", "   "));
            addTableToTable(mainTable, 4, mainFont, asList(" ", " ", " ", "  "));

            addCellToTable(mainTable, "Total Urban", headFont);
            addTableToTable(mainTable, 2, headFont, asList("Casnic", "Non-casnic"));
            addTableToTable(mainTable, 8, headFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   ", " ", " ", " ", "   "));
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "  ", " ", " ", " ", "  "));

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


        multiplyCodeForGarbageType(uat -> {
            addCellToTable(mainTable, uat, headFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"));
            addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   ", " ", " ", " ", "   "));
            addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "  ", " ", " ", " ", "  "));

        }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));

        addCellToTable(mainTable, "Total Rural", headFont);
        addTableToTable(mainTable, 2, headFont, asList("Casnic", "Non-casnic"));
        addTableToTable(mainTable, 8, headFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
        addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   ", " ", " ", " ", "   "));
        addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "  ", " ", " ", " ", "  "));
        document.add(mainTable);
        //TODO: ADD TOTAL RECYCLABLE RURAL
    }

    public void createUrbanOtherTypesOfGarbageTable7(Document document, Font headFont, Font mainFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);
        addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);

        addTableToTable(mainTable, 1, mainFont, asList("Blaj"));
        addTableToTable(mainTable, 4, headFont, asList("Voluminoase", "Periculoase", "Abandonate", "Deșeuri din construcții și demolări"));
        addTableToTable(mainTable, 4, mainFont, asList(" ", " ", " ", "   "));
        addTableToTable(mainTable, 4, mainFont, asList(" ", " ", " ", "   "));

        document.add(mainTable);
        //TODO: Add total urban
    }

    public void createRuralOtherTypesOfGarbageTable8(Document document, Font headFont, Font mainFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);
        addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);

        multiplyCodeForGarbageType(uat -> {
            addCellToTable(mainTable, uat, mainFont);
            addTableToTable(mainTable, 4, mainFont, asList("Voluminoase", "Periculoase", "Abandonate", "Deșeuri din construcții și demolări"));
            addTableToTable(mainTable, 4, mainFont, asList(" ", " ", " ", "   "));
            addTableToTable(mainTable, 4, mainFont, asList(" ", " ", " ", "   "));
        }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));
        document.add(mainTable);
        //TODO: ADD total
    }

    public void createSortingStationTable9(Document document, Font headFont, Font mainFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire stație sortare", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);


        addCellToTable(mainTable, "SS CMID Galda de Jos", headFont);
        addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"));
        addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
        addTableToTable(mainTable, 8, mainFont, asList(" ", " ", " ", "   ", " ", " ", " ", "   "));

        addCellToTable(mainTable, "Total 1", headFont);
        addCellToTable(mainTable, "Casnic", mainFont);
        addCellToTable(mainTable, "Reciclabil", mainFont);
        addCellToTable(mainTable, " ", mainFont);

        addCellToTable(mainTable, "Total 2", headFont);
        addCellToTable(mainTable, "Non-Casnic", mainFont);
        addCellToTable(mainTable, "Reciclabil", mainFont);
        addCellToTable(mainTable, " ", mainFont);

        document.add(mainTable);

        //TODO: TOTAL

    }


    public void createBioTreatmentTable10(Document document, Font headFont, Font mainFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire stație TMB", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);

        addCellToTable(mainTable, "TMB CMID Galda de Jos", headFont);
        addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
        addTableToTable(mainTable, 2, mainFont, asList("Rezidual", "Rezidual"));
        addTableToTable(mainTable, 2, mainFont, asList(" ", " "));

        addCellToTable(mainTable, "Total 1", headFont);
        addCellToTable(mainTable, "Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, " ", mainFont);

        addCellToTable(mainTable, "Total 2", headFont);
        addCellToTable(mainTable, "Non-Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, " ", mainFont);

        document.add(mainTable);
        //TODO: Add Total
    }

    public void createGarbageSubmittedToDepositTable11(Document document, Font headFont, Font mainFont) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(80);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire depozit", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);

        addCellToTable(mainTable, "CMID Galda de Jos", headFont);
        addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
        addTableToTable(mainTable, 2, mainFont, asList("Rezidual", "Rezidual"));
        addTableToTable(mainTable, 2, mainFont, asList(" ", " "));

        addCellToTable(mainTable, "Total 1", headFont);
        addCellToTable(mainTable, "Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, " ", mainFont);

        addCellToTable(mainTable, "Total 2", headFont);
        addCellToTable(mainTable, "Non-Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, " ", mainFont);

        //TODO add total

        document.add(mainTable);
    }

    public void createAdiSalubrisPaymentTable12(Document document, Font headFont, Font mainFont) {
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{3, 3, 3, 3});

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

            document.add(table);

        } catch (DocumentException ex) {

        }

    }



    public void createTrimestrialUatPaymentTable13(Document document, Font headFont, Font mainFont) {
        try {

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(95);
            table.setWidths(new float[]{2, 3, 10, 3, 3, 3});

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

            document.add(table);
        } catch (DocumentException ex) {
            System.out.println(ex);
        }
    }
}
