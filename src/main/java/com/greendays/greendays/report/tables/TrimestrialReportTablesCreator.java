package com.greendays.greendays.report.tables;

import com.greendays.greendays.model.dto.Destination;
import com.greendays.greendays.model.totals.TrimestrialReportData;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.security.core.parameters.P;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class TrimestrialReportTablesCreator extends TablesCreatorHelper {
    public static final String FONT = "/usr/greendays/src/main/resources/FreeSans.ttf";

    public void createFirstReportTable1(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) throws DocumentException {
        PdfPTable firstRow = new PdfPTable(2);
        firstRow.setWidthPercentage(100);
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

    public void createCentralisingTable2(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) {
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
                addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"), getHeightAdjustingConsumer("Casnic", 32));

                if (garbageType.equalsIgnoreCase("Total 1")) {
                    addTableToTable(mainTable, 4, mainFont, asList(
                            trimestrialReportData.getTotalByClientTypeDestinationAndRecyclableOrRezidual("casnic", Destination.TRANSFER_STATION).toString(),
                            trimestrialReportData.getTotalByClientTypeDestinationAndRecyclableOrRezidual("casnic", Destination.CMID_GALDA).toString(),
                            trimestrialReportData.getTotalByClientTypeDestinationAndRecyclableOrRezidual("non-casnic", Destination.TRANSFER_STATION).toString(),
                            trimestrialReportData.getTotalByClientTypeDestinationAndRecyclableOrRezidual("non-casnic", Destination.CMID_GALDA).toString()
                    ));
                    addTableToTable(mainTable, 4, mainFont, asList("CMID Galda de Jos", Destination.CMID_GALDA.getDestinationName(), "CMID Galda de Jos", Destination.CMID_GALDA.getDestinationName()));

                } else {
                    addTableToTable(mainTable, 4, mainFont, asList(
                            trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("casnic", garbageType, Destination.CMID_GALDA.getDestinationName()).toString(),
                            trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString(),
                            trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", garbageType, Destination.CMID_GALDA.getDestinationName()).toString(),
                            trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString())
                    );

                    addTableToTable(mainTable, 4, mainFont, asList("CMID Galda de Jos", Destination.CMID_GALDA.getDestinationName(), "CMID Galda de Jos", Destination.CMID_GALDA.getDestinationName()));
                }

            }, asList("Rezidual", "Hârtie și carton", "Plastic", "Metal", "Sticlă", "Total 1"));


            multiplyCodeForGarbageType(garbageType -> {
                addCellToTable(mainTable, garbageType, mainFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"), getHeightAdjustingConsumer("Casnic", 32));

                addTableToTable(mainTable, 4, mainFont, asList(
                        trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("casnic", garbageType, Destination.CMID_GALDA.getDestinationName()).toString(),
                        trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", garbageType, Destination.CMID_GALDA.getDestinationName()).toString(),
                        trimestrialReportData.getTotalForClientTypeGarbageNameAndDestination("non-casnic", garbageType, Destination.TRANSFER_STATION.getDestinationName()).toString())
                );

                addTableToTable(mainTable, 4, mainFont, asList("CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName()));
            }, asList("Voluminoase", "Periculoase", "Abandonate", "Deșeuri din construcții și demolări"));


            addCellToTable(mainTable, "Total 2", headFont);
            addCellToTable(mainTable, "", headFont);
            addCellToTable(mainTable, trimestrialReportData.getTotalOtherTypes(), headFont);
            addCellToTable(mainTable, Destination.TRANSFER_STATION.getDestinationName(), headFont);

            addCellToTable(mainTable, "Total general 1 + 2", headFont);
            addCellToTable(mainTable, "", headFont);
            addCellToTable(mainTable, trimestrialReportData.getTotalForTrimester(), headFont);
            addCellToTable(mainTable, "TMB/st.sortare/st.transfer", headFont);
            document.add(mainTable);


        } catch (Exception e) {

        }

    }

    public void createUrbanEnvTable3(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) {
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Categorie deșeu", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


            addCellToTable(mainTable, "Blaj", mainFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"), getHeightAdjustingConsumer("Casnic", 32));
            addTableToTable(mainTable, 4, mainFont, asList(
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString()));
            addTableToTable(mainTable, 4, mainFont, asList("CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName()));

            addCellToTable(mainTable, "Total urban", mainFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
            addTableToTable(mainTable, 2, mainFont, asList(
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "rezidual", "casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "rezidual", "non-casnic").toString()));
            addTableToTable(mainTable, 2, mainFont, asList("Statie transfer/TMB", "Statie transfer/TMB"));


            addCellToTable(mainTable, "Total Urban", headFont);
            addCellToTable(mainTable, " ", headFont);
            addCellToTable(mainTable, trimestrialReportData.getTotalByUatAndGarbageName("Blaj", "rezidual").toString(), headFont);
            addCellToTable(mainTable, " ", headFont);

            document.add(mainTable);
        } catch (Exception e) {
            System.out.println(e);
        }


    }


    public void createRuralEnvTable4(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) {
        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable mainTable = getCentralisingTable(4);

            addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


            multiplyCodeForGarbageType(uat -> {
                addCellToTable(mainTable, uat, mainFont);
                addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"), getHeightAdjustingConsumer("Casnic", 32));
                addTableToTable(mainTable, 4, mainFont, asList(
                        trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                        trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                        trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(),
                        trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "rezidual", Destination.TRANSFER_STATION.getDestinationName()).toString()));
                addTableToTable(mainTable, 4, mainFont, asList("CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName()));
            }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă", "Total 1"));


            addCellToTable(mainTable, "Total Rezidual Rural", headFont);
            addCellToTable(mainTable, " ", headFont);
            addCellToTable(mainTable, trimestrialReportData.getTotalRezidualRural(), headFont);
            addCellToTable(mainTable, " ", headFont);


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

    public void createRecyclableUrbanTable5(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) {

        try {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable mainTable = new PdfPTable(5);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new int[]{4, 4, 4, 4, 6});
            mainTable.setKeepTogether(true);


            addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
            addCellToTable(mainTable, "Tip beneficiar", headFont);
            addCellToTable(mainTable, "Categorie deșeu", headFont);
            addCellToTable(mainTable, "Cantitate (tone)", headFont);
            addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);

            addCellToTable(mainTable, "Blaj", headFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"), getHeightAdjustingConsumer("Casnic", 128));
            addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"), getHeightAdjustingConsumer(32));
            addTableToTable(mainTable, 16, mainFont, asList(
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination("Blaj", "non-casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString()
            ));
            addTableToTable(mainTable, 16, mainFont, asList("CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName()));

            addCellToTable(mainTable, "Total Urban", headFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"), getHeightAdjustingConsumer("Casnic", 64));
            addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
            addTableToTable(mainTable, 8, mainFont, asList(
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Hârtie și carton", "casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Plastic", "casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Metal", "casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Sticlă", "casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Hârtie și carton", "non-casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Plastic", "non-casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Metal", "non-casnic").toString(),
                    trimestrialReportData.getTotalByUatGarbageNameAndClientType("Blaj", "Sticlă", "non-casnic").toString()
            ));
            addTableToTable(mainTable, 8, mainFont, asList("St.transfer/St.sortare", "St.transfer/St.sortare", "St.transfer/St.sortare", "St.transfer/St.sortare", "St.transfer/St.sortare", "St.transfer/St.sortare", "St.transfer/St.sortare", "St.transfer/St.sortare"));

            addCellToTable(mainTable, "Total reciclabil urban", headFont);
            addCellToTable(mainTable, " ", headFont);
            addCellToTable(mainTable, " ", headFont);
            addCellToTable(mainTable, trimestrialReportData.getTotalRecyclableByUat("Blaj"), headFont);
            addCellToTable(mainTable, " ", headFont);

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

    private BiConsumer<PdfPCell, String> getHeightAdjustingConsumer(String cellContent) {
        return (cell, string) -> {
            if (string.equals(cellContent)) {
                cell.setFixedHeight(64);
            }
        };
    }

    private BiConsumer<PdfPCell, String> getHeightAdjustingConsumer(String cellContent, int height) {
        return (cell, string) -> {
            if (string.equals(cellContent)) {
                cell.setFixedHeight(height);
            }
        };
    }

    public void createRecyclableRuralTable6(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(5);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 4, 4, 4, 6});
        mainTable.setKeepTogether(true);


        addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);
        addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);


        multiplyCodeForGarbageType(uat -> {
            addCellToTable(mainTable, uat, headFont);
            addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"), getHeightAdjustingConsumer("Casnic", 128));
            addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"), getHeightAdjustingConsumer(32));
            addTableToTable(mainTable, 16, mainFont, asList(
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Hârtie și carton", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Hârtie și carton", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Plastic", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Plastic", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Metal", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Metal", Destination.TRANSFER_STATION.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Sticlă", Destination.CMID_GALDA.getDestinationName()).toString(),
                    trimestrialReportData.getTotalForUatClientTypeGarbageNameAndDestination(uat, "non-casnic", "Sticlă", Destination.TRANSFER_STATION.getDestinationName()).toString()
            ));
            addTableToTable(mainTable, 16, mainFont, asList("CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName()));

        }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă"));


        addCellToTable(mainTable, "Total Rural", headFont);
        addTableToTable(mainTable, 2, headFont, asList("Casnic", "Non-casnic"), getHeightAdjustingConsumer("Casnic", 64));
        addTableToTable(mainTable, 8, headFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
        addTableToTable(mainTable, 8, mainFont, asList(
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("casnic", "Hârtie și carton").toString(),
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("casnic", "Plastic").toString(),
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("casnic", "Metal").toString(),
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("casnic", "Sticlă").toString(),
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("non-casnic", "Hârtie și carton").toString(),
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("non-casnic", "Plastic").toString(),
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("non-casnic", "Metal").toString(),
                trimestrialReportData.getTotalRuralByClientTypeAndGarbageName("non-casnic", "Sticlă").toString()
        ));
        addTableToTable(mainTable, 8, mainFont, asList("St.Transfer/St.Sortare", "St.Transfer/St.Sortare", "St.Transfer/St.Sortare", "St.Transfer/St.Sortare", "St.Transfer/St.Sortare", "St.Transfer/St.Sortare", "St.Transfer/St.Sortare", "St.Transfer/St.Sortare"));

        addCellToTable(mainTable, "Total reciclabil rural", headFont);
        addCellToTable(mainTable, " ", headFont);
        addCellToTable(mainTable, " ", headFont);
        addCellToTable(mainTable, trimestrialReportData.getTotalRecyclableRural(), headFont);
        addCellToTable(mainTable, " ", headFont);

        document.add(mainTable);
    }

    public void createUrbanOtherTypesOfGarbageTable7(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);
        addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);

        addTableToTable(mainTable, 1, mainFont, asList("Blaj"));
        addTableToTable(mainTable, 4, headFont, asList("Voluminoase", "Periculoase", "Abandonate", "Deșeuri din construcții și demolări"), getHeightAdjustingConsumer(32));
        addTableToTable(mainTable, 8, mainFont, asList(
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Voluminoase", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Voluminoase", Destination.TRANSFER_STATION).toString(),
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Periculoase", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Periculoase", Destination.TRANSFER_STATION).toString(),
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Abandonate", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Abandonate", Destination.TRANSFER_STATION).toString(),
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Deșeuri din construcții și demolări", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByUatGarbageNameAndDestination("Blaj", "Deșeuri din construcții și demolări", Destination.TRANSFER_STATION).toString()
        ));
        addTableToTable(mainTable, 8, mainFont, asList("CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName()));


        addCellToTable(mainTable, "Total urban", headFont);
        addCellToTable(mainTable, " ", headFont);
        addCellToTable(mainTable, trimestrialReportData.getTotalOtherTypesUrban(), headFont);
        addCellToTable(mainTable, " ", headFont);

        document.add(mainTable);
    }

    public void createRuralOtherTypesOfGarbageTable8(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Unitate administrativ-teritorială", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);
        addCellToTable(mainTable, "Stație transfer \n -Destinație finală* ", headFont);

        multiplyCodeForGarbageType(uat -> {
            addTableToTable(mainTable, 1, mainFont, asList(uat));
            if (uat.equalsIgnoreCase("Total Rural")) {
                addTableToTable(mainTable, 4, headFont, asList("Voluminoase", "Periculoase", "Abandonate", "Deșeuri din construcții și demolări"));
                addTableToTable(mainTable, 4, mainFont, asList(
                        trimestrialReportData.getTotalRuralByGarbageName("Voluminoase").toString(),
                        trimestrialReportData.getTotalRuralByGarbageName("Periculoase").toString(),
                        trimestrialReportData.getTotalRuralByGarbageName("Abandonate").toString(),
                        trimestrialReportData.getTotalRuralByGarbageName("Deșeuri din construcții și demolări").toString()
                ));
                addTableToTable(mainTable, 4, mainFont, asList(" ", " ", " ", " "));

            } else {
                addTableToTable(mainTable, 4, headFont, asList("Voluminoase", "Periculoase", "Abandonate", "Deșeuri din construcții și demolări"), getHeightAdjustingConsumer(32));
                addTableToTable(mainTable, 8, mainFont, asList(
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Voluminoase", Destination.CMID_GALDA).toString(),
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Voluminoase", Destination.TRANSFER_STATION).toString(),
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Periculoase", Destination.CMID_GALDA).toString(),
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Periculoase", Destination.TRANSFER_STATION).toString(),
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Abandonate", Destination.CMID_GALDA).toString(),
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Abandonate", Destination.TRANSFER_STATION).toString(),
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Deșeuri din construcții și demolări", Destination.CMID_GALDA).toString(),
                        trimestrialReportData.getTotalByUatGarbageNameAndDestination(uat, "Deșeuri din construcții și demolări", Destination.TRANSFER_STATION).toString()
                ));
                addTableToTable(mainTable, 8, mainFont, asList("CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName(), "CMID Galda de Jos", Destination.TRANSFER_STATION.getDestinationName()));

            }

        }, asList("Crăciunelu de Jos", "Bucerdea Grânoasă", "Șona", "Jidvei", "Cergău", "Cenade", "Cetatea de Baltă", "Roșia de Secaș", "Sâncel", "Valea Lungă", "Total Rural"));
        document.add(mainTable);
    }

    public void createSortingStationTable9(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire stație sortare", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);


        addCellToTable(mainTable, "SS CMID Galda de Jos", headFont);
        addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-casnic"), getHeightAdjustingConsumer(64));
        addTableToTable(mainTable, 8, mainFont, asList("Hârtie și carton", "Plastic", "Metal", "Sticlă", "Hârtie și carton", "Plastic", "Metal", "Sticlă"));
        addTableToTable(mainTable, 8, mainFont, asList(
                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("casnic", "Hârtie și carton", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("casnic", "Plastic", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("casnic", "Metal", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("casnic", "Sticlă", Destination.CMID_GALDA).toString(),

                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("non-casnic", "Hârtie și carton", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("non-casnic", "Plastic", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("non-casnic", "Metal", Destination.CMID_GALDA).toString(),
                trimestrialReportData.getTotalByClientTypeAndGarbageNameRuralAndDestination("non-casnic", "Sticlă", Destination.CMID_GALDA).toString()
        ));

        addCellToTable(mainTable, "Total 1", headFont);
        addCellToTable(mainTable, "Casnic", mainFont);
        addCellToTable(mainTable, "Reciclabil", mainFont);
        addCellToTable(mainTable, trimestrialReportData.getTotalRecyclableByClientType("Casnic"), mainFont);

        addCellToTable(mainTable, "Total 2", headFont);
        addCellToTable(mainTable, "Non-Casnic", mainFont);
        addCellToTable(mainTable, "Reciclabil", mainFont);
        addCellToTable(mainTable, trimestrialReportData.getTotalRecyclableByClientType("Non-Casnic"), mainFont);


        addCellToTable(mainTable, "Total 1+2", headFont);
        addCellToTable(mainTable, " ", mainFont);
        addCellToTable(mainTable, " ", mainFont);
        addCellToTable(mainTable, trimestrialReportData.getTotalRecyclable(), mainFont);

        document.add(mainTable);
    }


    public void createBioTreatmentTable10(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire stație TMB", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);

        addCellToTable(mainTable, "TMB CMID Galda de Jos", headFont);
        addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
        addTableToTable(mainTable, 2, mainFont, asList("Rezidual", "Rezidual"));
        addTableToTable(mainTable, 2, mainFont, asList(trimestrialReportData.getTotalByGarbageNameClientTypeAndDestination("casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(), trimestrialReportData.getTotalByGarbageNameClientTypeAndDestination("non-casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString()));

        addCellToTable(mainTable, "Total 1", headFont);
        addCellToTable(mainTable, "Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, trimestrialReportData.getTotalByGarbageNameClientTypeAndDestination("casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(), mainFont);

        addCellToTable(mainTable, "Total 2", headFont);
        addCellToTable(mainTable, "Non-Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, trimestrialReportData.getTotalByGarbageNameClientTypeAndDestination("non-casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()).toString(), mainFont);

        addCellToTable(mainTable, "Total 1+2", headFont);
        addCellToTable(mainTable, " ", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable,
                trimestrialReportData.getTotalByGarbageNameClientTypeAndDestination("non-casnic", "rezidual", Destination.CMID_GALDA.getDestinationName())
                        .add(trimestrialReportData.getTotalByGarbageNameClientTypeAndDestination("casnic", "rezidual", Destination.CMID_GALDA.getDestinationName()))
                        .toString(), mainFont);

        document.add(mainTable);
    }

    public void createGarbageSubmittedToDepositTable11(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) throws DocumentException {
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable mainTable = new PdfPTable(4);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{6, 6, 6, 6});
        mainTable.setKeepTogether(true);

        addCellToTable(mainTable, "Denumire depozit", headFont);
        addCellToTable(mainTable, "Tip beneficiar", headFont);
        addCellToTable(mainTable, "Categorie deșeu", headFont);
        addCellToTable(mainTable, "Cantitate (tone)", headFont);

        addCellToTable(mainTable, "CMID Galda de Jos", headFont);
        addTableToTable(mainTable, 2, mainFont, asList("Casnic", "Non-Casnic"));
        addTableToTable(mainTable, 2, mainFont, asList("Rezidual", "Rezidual"));
        addTableToTable(mainTable, 2, mainFont, asList("nu exista date", "nu exista date"));

        addCellToTable(mainTable, "Total 1", headFont);
        addCellToTable(mainTable, "Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, "nu exista data", mainFont);

        addCellToTable(mainTable, "Total 2", headFont);
        addCellToTable(mainTable, "Non-Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, "nu exista date", mainFont);

        addCellToTable(mainTable, "Total 1+2", headFont);
        addCellToTable(mainTable, "Non-Casnic", mainFont);
        addCellToTable(mainTable, "Rezidual", mainFont);
        addCellToTable(mainTable, "nu exista date", mainFont);


        document.add(mainTable);
    }

    public void createAdiSalubrisPaymentTable12(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) {
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


    public void createTrimestrialUatPaymentTable13(Document document, Font headFont, Font mainFont, TrimestrialReportData trimestrialReportData) {
        try {

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(98);
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

            PdfPTable cifraDeAfaceriTable = new PdfPTable(4);
            cifraDeAfaceriTable.setWidths(new float[]{4, 5, 4, 4.6f});
            addCellToTable(cifraDeAfaceriTable, "2=3+4+5", mainFont);
            addCellToTable(cifraDeAfaceriTable, "3", mainFont);
            addCellToTable(cifraDeAfaceriTable, "4", mainFont);
            addCellToTable(cifraDeAfaceriTable, "5", mainFont);
            PdfPCell insideCell = new PdfPCell(cifraDeAfaceriTable);
            insideCell.setBorder(0);
            insideCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            insideCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            addCellToTable(table, "", mainFont);
            addCellToTable(table, "1", mainFont);
            table.addCell(insideCell);
            addCellToTable(table, "6", mainFont);
            addCellToTable(table, "7", mainFont);
            addCellToTable(table, "8", mainFont);


            document.add(table);
        } catch (DocumentException ex) {
            System.out.println(ex);
        }
//            ----------------------------------------------------

        //TODO: Add data to this table

    }

    private Consumer<PdfPCell> getHeightAdjustingConsumer(int height) {
        return (cell) -> cell.setFixedHeight(height);
    }

}
