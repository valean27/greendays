package com.greendays.greendays.report.tables;

import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.dto.Destination;
import com.greendays.greendays.model.totals.MonthlyReportData;
import com.greendays.greendays.model.totals.TrimestrialReportData;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.Arrays.asList;


public class MonthlyReportTables extends TablesCreatorHelper {

    public void createHeaderTable1(Document document, Font mainFont, Font headFont, String referenceDate, MonthlyReportData monthlyReportData) throws DocumentException {
        PdfPTable firstRow = new PdfPTable(2);
        firstRow.setWidthPercentage(100);
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

    public void createUrbanResidualGarbageTable2(Document document, Font mainFont, Font headFont, MonthlyReportData monthlyReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(3);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 5, 1});
        mainTable.setKeepTogether(true);

        addCellToTableAlignedCenter(mainTable, "UAT", headFont);
        addStrangeTableToTable(mainTable, headFont);
        addCellToTable(mainTable, "Obs.", headFont);

        BigDecimal total = monthlyReportData.getRezidualGarbageReportsByUat("Blaj").stream().map(DailyReportDto::getQuantity).reduce(BigDecimal.valueOf(0D), BigDecimal::add);
        BigDecimal casnici = monthlyReportData.getRezidualGarbageReportsByUatAndClientType("Blaj", "casnic").stream().map(DailyReportDto::getQuantity).reduce(BigDecimal.valueOf(0D), BigDecimal::add);
        BigDecimal nonCasnici = monthlyReportData.getRezidualGarbageReportsByUatAndClientType("Blaj", "non-casnic").stream().map(DailyReportDto::getQuantity).reduce(BigDecimal.valueOf(0D), BigDecimal::add);

        addCellToTableAlignedCenter(mainTable, "Blaj", mainFont);
        addTableToTable(mainTable, 3, 3, mainFont, asList(total.toString(), casnici.toString(), nonCasnici.toString()));
        addCellToTable(mainTable, " ", mainFont);

        document.add(mainTable);
    }


    public void createRuralResidualGarbageTable3(Document document, Font mainFont, Font headFont, MonthlyReportData monthlyReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(3);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 5, 1});
        mainTable.setKeepTogether(true);

        addCellToTableAlignedCenter(mainTable, "UAT", headFont);
        addStrangeTableToTable(mainTable, headFont);
        addCellToTable(mainTable, "Obs.", headFont);

        multiplyCodeForGarbageType(uat -> {
            addCellToTableAlignedCenter(mainTable, uat, mainFont);
            addTableToTable(mainTable, 3, 3, mainFont,
                    asList(monthlyReportData.getTotalForUatAndGarbageName(uat, "rezidual").toString(),
                            monthlyReportData.getTotalForUatClientTypeAndGarbageName(uat, "casnic", "rezidual").toString(),
                            monthlyReportData.getTotalForUatClientTypeAndGarbageName(uat, "non-casnic", "rezidual").toString()));

            addCellToTable(mainTable, " ", mainFont);
        }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));

        document.add(mainTable);
    }

    public void createUrbanRecyclableGarbageTable4(Document document, Font mainFont, Font headFont, MonthlyReportData monthlyReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(3);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 5, 1});
        mainTable.setKeepTogether(true);

        addCellToTableAlignedCenter(mainTable, "UAT", headFont);
        addStrangeTableToTable(mainTable, headFont);
        addCellToTable(mainTable, "Obs.", headFont);

        addCellToTableAlignedCenter(mainTable, "Blaj", mainFont);
        addTableToTable(mainTable, 3, 3, mainFont, asList(monthlyReportData.getTotalRecyclableByUat("Blaj").toString(), monthlyReportData.getTotalRecyclableByUatAndClientType("Blaj", "casnic").toString(), monthlyReportData.getTotalRecyclableByUatAndClientType("Blaj", "non-casnic").toString()));
        addCellToTable(mainTable, " ", mainFont);

        document.add(mainTable);
    }


    public void createRuralRecyclableGarbageTable5(Document document, Font mainFont, Font headFont, MonthlyReportData monthlyReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(3);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 5, 1});
        mainTable.setKeepTogether(true);

        addCellToTableAlignedCenter(mainTable, "UAT", headFont);
        addStrangeTableToTable(mainTable, headFont);
        addCellToTable(mainTable, "Obs.", headFont);

        multiplyCodeForGarbageType(uat -> {
            addCellToTableAlignedCenter(mainTable, uat, mainFont);
            addTableToTable(mainTable, 3, 3, mainFont, asList(monthlyReportData.getTotalRecyclableByUat(uat).toString(), monthlyReportData.getTotalRecyclableByUatAndClientType(uat, "casnic").toString(), monthlyReportData.getTotalRecyclableByUatAndClientType(uat, "non-casnic").toString()));
            addCellToTable(mainTable, " ", mainFont);
        }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));

        document.add(mainTable);
    }

    public void createTransferStationSubmittedGarbageTable6(Document document, Font mainFont, Font headFont, MonthlyReportData monthlyReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 3, 3, 2});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire stație transfer", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Cantitate", headFont);


        addCellToTableAlignedCenter(mainTable, "Blaj", headFont);
        addTableToTable(mainTable, 5, mainFont, asList("Rezidual", "Hartie, carton", "Plastic", "Metal", "Sticla"), (pdfPCell, s) -> pdfPCell.setFixedHeight(32));
        addTableToTable(mainTable, 10, mainFont, asList("casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici"));
        addTableToTable(mainTable, 10, mainFont, asList(
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Rezidual", "casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Rezidual", "non-casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Hârtie și carton", "casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Hârtie și carton", "non-casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Plastic", "casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Plastic", "non-casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Metal", "casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Metal", "non-casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Sticlă", "casnic").toString(),
                monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Sticlă", "non-casnic").toString()));

        document.add(mainTable);
        //TODO: add total
    }

    public void createDepositSubmittedGarbageTable7(Document document, Font mainFont, Font headFont, MonthlyReportData monthlyReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 3, 3, 2});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire depozit", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Cantitate", headFont);


        addCellToTableAlignedCenter(mainTable, "Galda de Jos", headFont);
        addTableToTable(mainTable, 5, mainFont, asList("Rezidual", "Hartie, carton", "Plastic", "Metal", "Sticla"), (pdfPCell, s) -> pdfPCell.setFixedHeight(32));
        addTableToTable(mainTable, 10, mainFont, asList("casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici", "casnici", "non-casnici"));
        addTableToTable(mainTable, 10, mainFont, asList(monthlyReportData.getTotalTransferStationByGarbageNameAndClientType("Rezidual", "casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Rezidual", "non-casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Hârtie și carton", "casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Hârtie și carton", "non-casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Plastic", "casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Plastic", "non-casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Metal", "casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Metal", "non-casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Sticlă", "casnic").toString(),
                monthlyReportData.getTotalCMIDGaldaSubmitedByGarbageNameAndClientType("Sticlă", "non-casnic").toString()));

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

    public void createCentralisingTable1(Document document, Font headFont, Font mainFont, MonthlyReportData monthlyReportData) {
        //todo: add according values
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Categorie deșeu", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Destinație", headFont);

            multiplyCodeForGarbageType(garbageType -> {
                addCellToTable(mainTable, garbageType, mainFont);
                addTableToTable(mainTable, 4, mainFont, asList(
                        monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", garbageType, Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", garbageType, Destination.CMID_GALDA.getDestinationName()).toString()
                ));
                addTableToTable(mainTable, 2, mainFont, asList("Casnici", "Non-Casnici"), getHeightAdjustingConsumer("Casnici", 32));

                if (garbageType.equalsIgnoreCase("rezidual")) {
                    addTableToTable(mainTable, 4, mainFont, asList("statie transfer", "TMB", "statie transfer", "TMB"));
                } else {
                    addTableToTable(mainTable, 4, mainFont, asList("statie transfer", "depozit", "statie transfer", "depozit"));
                }
            }, asList("Rezidual", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));


            document.add(mainTable);

            addParagraphToDocument(document, headFont, format("total rezidual st. transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("rezidual", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total rezidual depozit\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("rezidual", Destination.CMID_GALDA.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total plastic st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("plastic", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total plastic depozit\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("plastic", Destination.CMID_GALDA.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total carton st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total carton depozit\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Hârtie și carton", Destination.CMID_GALDA.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total sticla st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Sticlă", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total sticla depozit\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Sticlă", Destination.CMID_GALDA.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total rezidual =\t %s", monthlyReportData.getTotalByGarbageName("rezidual")));
            addParagraphToDocument(document, headFont, format("total plastic =\t %s", monthlyReportData.getTotalByGarbageName("plastic")));
            addParagraphToDocument(document, headFont, format("total carton =\t %s", monthlyReportData.getTotalByGarbageName("Hârtie și carton")));
            addParagraphToDocument(document, headFont, format("total sticla =\t %s", monthlyReportData.getTotalByGarbageName("Sticlă")));
            addParagraphToDocument(document, headFont, format("total reciclabil =\t %s", monthlyReportData.getTotalRecyclable()));

        } catch (Exception e) {

        }

    }

    public void createResidualTable2(Document document, Font headFont, Font mainFont, MonthlyReportData monthlyReportData) {
        //todo: add according values
        try {
            addParagraphToDocument(document, headFont,
                    "\t\n" +
                            " Tabel 2 : Deseuri reziduale colectate\t\n" +
                            "\t");

            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Unitatea administrativ teritoriala", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Destinație", headFont);

            multiplyCodeForGarbageType(uat -> {
                addCellToTable(mainTable, uat, headFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnici", "Non-Casnici"), getHeightAdjustingConsumer("Casnici", 32));

                addTableToTable(mainTable, 4, mainFont, asList(
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString()
                ));

                addTableToTable(mainTable, 4, mainFont, asList("statie transfer", "TMB", "statie transfer", "TMB"));
            }, asList("Blaj", "Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));


            document.add(mainTable);

            addParagraphToDocument(document, headFont, format("Total = %s ", monthlyReportData.getTotalByGarbageName("rezidual")));

        } catch (Exception e) {

        }

    }


    public void createRecyclableTable3(Document document, Font headFont, Font mainFont, MonthlyReportData monthlyReportData) {
        //todo: add according values
        try {
            addParagraphToDocument(document, headFont,
                    "\t\n" +
                            " Tabel 3 : Deseuri reziduale colectate\t\n" +
                            "\t");

            PdfPTable mainTable = new PdfPTable(5);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new int[]{4, 4, 4, 4, 4});
            mainTable.setKeepTogether(true);

            addCellToTable(mainTable, "Unitatea administrativ teritoriala", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Destinație", headFont);
            addCellToTable(mainTable, "Categorie deseu", headFont);

            multiplyCodeForGarbageType(uat -> {
                addCellToTable(mainTable, uat, headFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnici", "Non-Casnici"), getHeightAdjustingConsumer("Casnici", 32 * 4));
                addTableToTable(mainTable, 16, mainFont, asList(
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString(),

                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),

                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString(),

                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString()
                ));

                addTableToTable(mainTable, 16, mainFont, asList(
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "depozit",
                        "depozit",
                        "depozit",
                        "depozit",
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "depozit",
                        "depozit",
                        "depozit",
                        "depozit"));

                addTableToTable(mainTable, 16, mainFont, asList(
                        "Hârtie și carton", "Plastic", "Metal", "Sticlă",
                        "Hârtie și carton", "Plastic", "Metal", "Sticlă",
                        "Hârtie și carton", "Plastic", "Metal", "Sticlă",
                        "Hârtie și carton", "Plastic", "Metal", "Sticlă"
                ));
            }, asList("Blaj", "Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));


            document.add(mainTable);

            addParagraphToDocument(document, headFont, format("Total = %s ", monthlyReportData.getTotalRecyclable()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createOtherTypesTable4(Document document, Font headFont, Font mainFont, MonthlyReportData monthlyReportData) {
        //todo: add according values
        try {
            addParagraphToDocument(document, headFont, "\nTabel 4 : Deseuri reziduale colectate");

            PdfPTable mainTable = new PdfPTable(5);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new int[]{4, 3, 3, 4, 8});
            mainTable.setKeepTogether(true);

            addCellToTable(mainTable, "Unitatea administrativ teritoriala", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Destinație", headFont);
            addCellToTable(mainTable, "Categorie deseu", headFont);


            multiplyCodeForGarbageType(uat -> {
                addCellToTable(mainTable, uat, headFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnici", "Non-Casnici"), getHeightAdjustingConsumer("Casnici", 32 * 4));
                addTableToTable(mainTable, 16, mainFont, asList(
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Voluminoase", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Periculoase", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Abandonate", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Deșeuri din construcții și demolări", Destination.TRANSFER_STATION.getDestinationName()).toString(),

                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Voluminoase", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Periculoase", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Abandonate", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Casnic", "Deșeuri din construcții și demolări", Destination.CMID_GALDA.getDestinationName()).toString(),

                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Voluminoase", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Periculoase", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Abandonate", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Deșeuri din construcții și demolări", Destination.TRANSFER_STATION.getDestinationName()).toString(),

                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Voluminoase", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Periculoase", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Abandonate", Destination.CMID_GALDA.getDestinationName()).toString(),
                        monthlyReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "Non-Casnic", "Deșeuri din construcții și demolări", Destination.CMID_GALDA.getDestinationName()).toString()
                ));

                addTableToTable(mainTable, 16, mainFont, asList(
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "depozit",
                        "depozit",
                        "depozit",
                        "depozit",
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "statie transfer",
                        "depozit",
                        "depozit",
                        "depozit",
                        "depozit"));


                addTableToTable(mainTable, 16, mainFont, asList(
                        "Voluminoase",
                        "Periculoase",
                        "Abandonate",
                        "Deșeuri din construcții și demolări",

                        "Voluminoase",
                        "Periculoase",
                        "Abandonate",
                        "Deșeuri din construcții și demolări",

                        "Voluminoase",
                        "Periculoase",
                        "Abandonate",
                        "Deșeuri din construcții și demolări",

                        "Voluminoase",
                        "Periculoase",
                        "Abandonate",
                        "Deșeuri din construcții și demolări"
                ));

            }, asList("Blaj", "Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));


            document.add(mainTable);

            addParagraphToDocument(document, headFont, format("Total = %s ", monthlyReportData.getTotalOtherTypes()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void createTransferStationTable5(Document document, Font headFont, Font mainFont, MonthlyReportData monthlyReportData) {
        //todo: add according values
        try {
            addParagraphToDocument(document, headFont, "\nTabel 5 : Deseuri predate statiei de transfer ");

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Denumire statie de transfer", headFont);
            addCellToTable(mainTable, "Categorie deseu", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate", headFont);

            addCellToTable(mainTable, "Statie Blaj", headFont);

            addTableToTable(mainTable, 5, mainFont, asList("Rezidual", "Hârtie și carton", "Plastic", "Metal", "Sticlă"), getHeightAdjustingConsumer(32));
            addTableToTable(mainTable, 10, mainFont, asList("Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici"));
            addTableToTable(mainTable, 10, mainFont, asList(
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", "Rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", "Rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString()

            ));

//            multiplyCodeForGarbageType(garbageType -> {
//                addCellToTable(mainTable, garbageType, mainFont);
//                addTableToTable(mainTable, 2, mainFont, asList("Casnici", "Non-Casnici"), getHeightAdjustingConsumer("Casnici", 32));
//                addTableToTable(mainTable, 2, mainFont, asList(
//                        monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("Casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString(),
//                        monthlyReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString()
//                ));
//
//            }, asList("Rezidual", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
//

            document.add(mainTable);

            addParagraphToDocument(document, headFont, format("total rezidual st. transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("rezidual", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total plastic st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("plastic", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total carton st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total sticla st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Sticlă", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total reciclabil statie =\t %s", monthlyReportData.getTotalRecyclableByDestination(Destination.TRANSFER_STATION)));

        } catch (Exception e) {

        }

    }


    public void createSortingStationTable6(Document document, Font headFont, Font mainFont, MonthlyReportData monthlyReportData) {
        //todo: add according values
        try {
            document.add(new Paragraph("\n"));
            addParagraphToDocument(document, headFont, "Tabel 5 : Deseuri predate statiei de sortare ");


            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Denumire statie de sortare", headFont);
            addCellToTable(mainTable, "Categorie deseu", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate", headFont);

            addCellToTable(mainTable, "Statia Galda de Jos", headFont);

            addTableToTable(mainTable, 5, mainFont, asList("Rezidual", "Hârtie și carton", "Plastic", "Metal", "Sticlă"), getHeightAdjustingConsumer(32));
            addTableToTable(mainTable, 10, mainFont, asList("Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici"));
            addTableToTable(mainTable, 10, mainFont, asList(
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("Casnic", "Rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("non-casnic", "Rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("Casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("non-casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("Casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("non-casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("Casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("non-casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("Casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeRecyclableAndDestination("non-casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString()

            ));

            document.add(mainTable);

            addParagraphToDocument(document, headFont, format("total rezidual st. transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("rezidual", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total plastic st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("plastic", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total carton st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total sticla st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Sticlă", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total reciclabil statie =\t %s", monthlyReportData.getTotalRecyclableByDestination(Destination.TRANSFER_STATION)));

        } catch (Exception e) {

        }

    }

    public void createDepositTable7(Document document, Font headFont, Font mainFont, MonthlyReportData monthlyReportData) {
        //todo: add according values
        try {
            document.add(new Paragraph("\n"));
            addParagraphToDocument(document, headFont, "Tabel 8 : Deseuri predate depozitului/depozitelor de deseuri");


            PdfPTable mainTable = getCentralisingTable(4);


            addCellToTable(mainTable, "Denumire depozit", headFont);
            addCellToTable(mainTable, "Categorie deseu", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate", headFont);

            addCellToTable(mainTable, "Galda de Jos", headFont);

            addTableToTable(mainTable, 5, mainFont, asList("Rezidual", "Hârtie și carton", "Plastic", "Metal", "Sticlă"), getHeightAdjustingConsumer(32));
            addTableToTable(mainTable, 10, mainFont, asList("Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici", "Casnici", "Non-Casnici"));
            addTableToTable(mainTable, 10, mainFont, asList(
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("Casnic", "Rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("non-casnic", "Rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("Casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("non-casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("Casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("non-casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("Casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("non-casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("Casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),
                    monthlyReportData.getTotalForClientTypeResidualAndDestination("non-casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString()

            ));

            document.add(mainTable);

            addParagraphToDocument(document, headFont, format("total rezidual st. transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("rezidual", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total plastic st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("plastic", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total carton st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total sticla st.transfer\t %s", monthlyReportData.getTotalForGarbageTypeAndDestination("Sticlă", Destination.TRANSFER_STATION.getDestinationName())));
            addParagraphToDocument(document, headFont, format("total reciclabil statie =\t %s", monthlyReportData.getTotalRecyclableByDestination(Destination.TRANSFER_STATION)));

        } catch (Exception e) {

        }

    }

    private BiConsumer<PdfPCell, String> getHeightAdjustingConsumer(String cellContent, int height) {
        return (cell, string) -> {
            if (string.equals(cellContent)) {
                cell.setFixedHeight(height);
            }
        };
    }


    private Consumer<PdfPCell> getHeightAdjustingConsumer(int height) {
        return (cell) -> cell.setFixedHeight(height);
    }

    private void addParagraphToDocument(Document document, Font font, String phrase) throws DocumentException {
        document.add(new Paragraph(phrase, font));
    }

}
