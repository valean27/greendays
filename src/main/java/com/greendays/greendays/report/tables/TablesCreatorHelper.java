package com.greendays.greendays.report.tables;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TablesCreatorHelper {
    protected PdfPTable getCentralisingTable(int colNumber) throws DocumentException {
        PdfPTable mainTable = new PdfPTable(colNumber);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new int[]{4, 4, 4, 4});
        mainTable.setKeepTogether(false);
        return mainTable;
    }

    protected void addCellToTable(PdfPTable pdfPTable, String phrase, Font font) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase, font));
        pdfPTable.addCell(pdfPCell);
    }

    protected void addCellToTableNoBorder(PdfPTable pdfPTable, String phrase, Font font) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase, font));
        pdfPCell.setBorder(0);
        pdfPTable.addCell(pdfPCell);
    }

    protected void addCellToTable(PdfPTable pdfPTable, String phrase, Font font, int minHeight) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase, font));
        pdfPTable.addCell(pdfPCell);
        pdfPCell.setMinimumHeight(20f);
    }

    protected void addCellToTableAlignedCenter(PdfPTable pdfPTable, String phrase, Font font) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(phrase, font));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPTable.addCell(pdfPCell);
        pdfPCell.setMinimumHeight(20f);
    }

    protected void addTableToTable(PdfPTable pdfPTable, int rowNumber, Font font, java.util.List<String> args) {
        PdfPTable innerTable = new PdfPTable(1);
        for (int i = 0; i < rowNumber; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(args.get(i), font));
            innerTable.addCell(cell);
        }
        PdfPCell innerCell = new PdfPCell(innerTable);
        innerCell.setBorder(0);
        pdfPTable.addCell(innerCell);
    }

    protected void addTableToTable(PdfPTable pdfPTable, int rowNumber, Font font, java.util.List<String> args, BiConsumer<PdfPCell, String> applyMappingOnCellName) {
        PdfPTable innerTable = new PdfPTable(1);
        for (int i = 0; i < rowNumber; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(args.get(i), font));
            applyMappingOnCellName.accept(cell, args.get(i));
            innerTable.addCell(cell);
        }
        PdfPCell innerCell = new PdfPCell(innerTable);
        innerCell.setBorder(0);
        pdfPTable.addCell(innerCell);
    }

    protected void addTableToTable(PdfPTable pdfPTable, int rowNumber, Font font, java.util.List<String> args, Consumer<PdfPCell> applyMappingOnAllCels) {
        PdfPTable innerTable = new PdfPTable(1);
        for (int i = 0; i < rowNumber; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(args.get(i), font));
            applyMappingOnAllCels.accept(cell);
            innerTable.addCell(cell);
        }
        PdfPCell innerCell = new PdfPCell(innerTable);
        innerCell.setBorder(0);
        pdfPTable.addCell(innerCell);
    }


    protected void addTableToTable(PdfPTable pdfPTable, int rowNumber, Font font, java.util.List<String> args, int start, int replyInterval) {
        PdfPTable innerTable = new PdfPTable(1);
        int next = 0;
        for (int i = 0; i < rowNumber; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(args.get(i), font));
            if (i == start) {
                cell.setFixedHeight(32);
                next = start + replyInterval;
            }

            if (i > start && i == next) {
                cell.setFixedHeight(32);
                next += replyInterval;
            }
            innerTable.addCell(cell);
        }
        PdfPCell innerCell = new PdfPCell(innerTable);
        innerCell.setBorder(0);
        pdfPTable.addCell(innerCell);
    }


    protected void addTableToTable(PdfPTable pdfPTable, int rowNumber, int colNumber, Font font, java.util.List<String> args) {
        PdfPTable innerTable = new PdfPTable(colNumber);
        for (int i = 0; i < rowNumber; i++) {
            PdfPCell cell = new PdfPCell(new Phrase(args.get(i), font));
            innerTable.addCell(cell);
        }
        PdfPCell innerCell = new PdfPCell(innerTable);
        innerCell.setBorder(0);
        pdfPTable.addCell(innerCell);
    }


    protected void multiplyCodeForGarbageType(Consumer<String> code, java.util.List<String> args) {
        for (String arg : args) {
            code.accept(arg);
        }
    }
}
