package com.greendays.greendays.service;

import com.greendays.greendays.mapper.DailyReportEntityToDailyReportDtoMapper;
import com.greendays.greendays.model.dto.DailyReportDto;
import com.greendays.greendays.model.dto.Trimester;
import com.greendays.greendays.model.totals.MonthlyReportData;
import com.greendays.greendays.model.totals.TrimestrialReportData;
import com.greendays.greendays.report.tables.MonthlyReportTables;
import com.greendays.greendays.report.tables.TrimestrialReportTablesCreator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfReportGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfReportGenerator.class.getName());
    public static final String FONT = "src/main/resources/FreeSans.ttf";
    public static final String FONT_BOLD = "src/main/resources/FreeSansBold.ttf";

    @Autowired
    private DailyReportService dailyReportService;

    public ByteArrayInputStream generateTrimestrialPdfReport(Trimester trimester) {
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

        List<DailyReportDto> dailyReportsOfTheQuarter = dailyReportService.getAllReportsForQuarter(trimester, LocalDate.now().getYear()).stream()
                .map(DailyReportEntityToDailyReportDtoMapper::mapEntityToDto)
                .collect(Collectors.toList());

        TrimestrialReportData trimestrialReportData = new TrimestrialReportData(dailyReportsOfTheQuarter, trimester);


        TrimestrialReportTablesCreator trimestrialReportTablesCreator = new TrimestrialReportTablesCreator();
        try {

            document.add(new Paragraph("Raport trimestrial\n \n" +
                    "Perioada de raportare: 01.12.2021 ??? 28.02.2022\n", boldFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            trimestrialReportTablesCreator.createFirstReportTable1(document, boldFont, font, trimestrialReportData);

            addParagraphToDocument(document, font, "\n");
            addParagraphToDocument(document, boldFont, "Sec??iunea I ??? Aspecte tehnice\n" +
                    "Capitolul 1. Cantit????i de de??euri municipale colectate\n");
            addParagraphToDocument(document, font, "Operatorul delegat prezint?? un tabel centralizator privind cantit????ile de de??euri municipale colectate din aria deleg??rii ??i defalcat?? pe surse (beneficiari casnici ??i non-casnici) pentru trimestrul aferent raportului. ");
            addParagraphToDocument(document, boldFont, "Tabel 1: Tabel centralizator de??euri colectate\n\n\n");

            trimestrialReportTablesCreator.createCentralisingTable2(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, font, "\n");
            addParagraphToDocument(document, font, "Operatorul delegat prezint?? tabele privind cantit????ile de de??euri colectate, pe tipuri de de??euri, pentru trimestrul aferent raportului:\n");
            addParagraphToDocument(document, boldFont, "1.1. De??euri reziduale colectate\n" +
                    "1.1.1. Mediul urban:\n" +
                    "Tabel 2: De??euri reziduale colectate\n");
            trimestrialReportTablesCreator.createUrbanEnvTable3(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, font, "Not??:\t\t\n" +
                    "* TMB, depozitare\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "1.1.2. Mediul rural:\n" +
                    "Tabel 3: De??euri reziduale colectate \n");
            trimestrialReportTablesCreator.createRuralEnvTable4(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, boldFont, "Not??:\t\t\n" +
                    "* TMB, depozitare\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "       1.2. De??euri reciclabile colectate\n" +
                    "1.2.1. Mediul urban: \n" +
                    "Tabel 4: De??euri reciclabile colectate separat\n");
            trimestrialReportTablesCreator.createRecyclableUrbanTable5(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, boldFont, "Not??:\n" +
                    "* sta??ie sortare, depozitare\n" +
                    "\n" +
                    "1.2.2. Mediul rural: \n" +
                    "Tabel 5: De??euri reciclabile colectate separat\n");
            trimestrialReportTablesCreator.createRecyclableRuralTable6(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, boldFont, "Not??:\n" +
                    "* sta??ie sortare, depozitare\n" +
                    "1.3. Alte tipuri de de??euri colectate\n" +
                    "1.3.1. Mediul urban: \n" +
                    "\n" +
                    "Tabel 6: Alte tipuri de de??euri colectate\n");
            trimestrialReportTablesCreator.createUrbanOtherTypesOfGarbageTable7(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, boldFont, "Not??:\n" +
                    "* CMID Galda de Jos\n" +
                    "1.3.2. Mediul rural: \n" +
                    "\n" +
                    "Tabel 7: Alte tipuri de de??euri colectate\n");
            trimestrialReportTablesCreator.createRuralOtherTypesOfGarbageTable8(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, boldFont, "\n" +
                    "  Not??:\n" +
                    "* CMID Galda de Jos\n" +
                    "Capitolul 2. Cantit????i de de??euri municipale colectate ??i predate altor operatori/instala??ii\n" +
                    "Operatorul prezint?? un tabel centralizator privind cantit????ile de de??euri municipale colectate ??i predate altor operatori/instala??ii.\n" +
                    "\n" +
                    "Tabel 8: De??euri predate sta??iei de sortare\n" +
                    "\n");
            trimestrialReportTablesCreator.createSortingStationTable9(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, boldFont, "\nTabel 9: De??euri predate sta??iei de tratare mecano-biologic??\n");
            trimestrialReportTablesCreator.createBioTreatmentTable10(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, boldFont, "\nTabel 10: De??euri predate depozitului de de??euri\n");
            trimestrialReportTablesCreator.createGarbageSubmittedToDepositTable11(document, boldFont, font, trimestrialReportData);
            addParagraphToDocument(document, font, "\nOperatorul prezint?? situa??ia cantit????ilor de de??euri colectate din locuri neamenajate/nepermise, precum ??i a de??eurilor respinse la colectare ??i cauzele respingerii.\n" +
                    "Operatorul prezint?? o list?? a tuturor beneficiarilor cu care au un contract de prest??ri de servicii ??ncheiat.\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "\nCapitolul 3. Indicatori de performan????\n");
            addParagraphToDocument(document, font, "Delegatul va raporta trimestrial Delegatarului (ADI Salubris Alba) performan??a realizat?? ??n ceea ce prive??te respectarea indicatorilor de performan???? stabili??i pe o baz?? trimestrial??, dup?? caz, precum ??i modul de ??ndeplinire a programului de investi??ii asumat prin contractul de delegare.\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "Sec??iunea II ??? Aspecte financiare     \n");
            addParagraphToDocument(document, boldFont, "\nCapitolul 4. Sume calculate ??i pl??tite pentru opera??iunile de tratare/eliminare a de??eurilor\n");
            addParagraphToDocument(document, font, "\nOperatorul delegat va raporta trimestrial Delegatarului (ADI Salubris Alba), urm??toarele documente:\n" +
                    "1. Contractele ??ncheiate cu operatorul instala??iilor;\n" +
                    "2. Dovada pl????ii tarifelor pentru activit????ile de tratare a de??eurilor.\n" +
                    "\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "\n" +
                    "\n" +
                    "Capitolul 5. Redeven??a ??i taxa de administrare\n" +
                    "        1. Redeven??a\n" +
                    "   \n\n   Tabel 10: Calculul ??i plata redeven??ei datorate UAT-urilor pentru trimestrul ...\n");
            trimestrialReportTablesCreator.createTrimestrialUatPaymentTable13(document, boldFont, font, trimestrialReportData);

            addParagraphToDocument(document, boldFont, "        2. Taxa de administrare \n" +
                    "       Tabel 11: Calculul ??i plata taxei de administrare datorat?? c??tre ADI Salubris Alba pentru trimestrul ...\n");

            trimestrialReportTablesCreator.createAdiSalubrisPaymentTable12(document, boldFont, font, trimestrialReportData);

            addParagraphToDocument(document, font, "\n" +
                    "       Observa??ii:   (dac?? este cazul).\n");

            addParagraphToDocument(document, boldFont, "\nCapitolul. 6 Probleme ??nt??mpinate ??n prestarea serviciului\n" +
                    "??ntreruperea activit????ii de colectare\n");
            addParagraphToDocument(document, font, "\n??n situa??ia ??n care apar ??ntreruperi ale activit????ii de colectare ??n trimestrul aferent raport??rii, Operatorul anexeaz?? urm??toarele:\n" +
                    "- ??nregistr??ri ale problemelor ??nt??mpinate ??n ziua respectiv??: \n" +
                    "- ??ntreruperi programate ??i/sau neprogramate;\n" +
                    "- defec??iuni ??i accidente;\n" +
                    "- timpul de oprire al autospecialelor sau unor subansamble ale acestora;\n" +
                    "- natura fiec??rei defec??iuni;\n" +
                    "- activit????ile de repara??ie/??ntre??inere;\n" +
                    "- ??nlocuiri ale vehiculelor, utilajelor, containerelor sau personalului;\n" +
                    "- condi??ii meteo, etc.\n" +
                    "- ??nregistr??ri ale lucr??rilor de ??ntre??inere ??i repara??ii la autospeciale sau alte vehicule, puncte de      colectare ??i containere;  \n" +
                    "- Rapoarte ale personalului desemnat pentru supervizare ??i monitorizare;\n" +
                    "    \nRapoarte ale conduc??torilor de autovehicule ??i operatorilor de pe autovehicule: \n" +
                    "- vehicule utilizate/neutilizate;\n" +
                    "- motive pentru scoaterea din uz a vehiculelor;\n" +
                    "- media sarcinilor utile;\n" +
                    "- distan??a parcurs?? etc.\n" +
                    "\n" +
                    "\n  Starea containerelor ??i a punctelor de colectare\n" +
                    "- Operatorul delegat prezint?? o situa??ie privind starea containerelor ??i a punctelor de colectare individuale ??i colective pentru fiecare comun??, ora?? ??i municipiu: \n" +
                    "- Num??rul ??i tipul containerelor plasate;\n" +
                    "- Num??rul containerelor reparate; \n" +
                    "- Num??rul containerelor ??nlocuite;\n" +
                    "- Num??rul containerelor furate, distruse ??i avariate; \n" +
                    "- Num??rul punctelor de colectare avariate ??i reparate;\n" +
                    "- Num??rul punctelor de colectare care au trebuit mai ??nt??i s?? fie cur????ate ??nainte ca vehiculul s?? ajung?? la containere.\n \n \n \n");

            addParagraphToDocument(document, boldFont, "Reclama??ii ");

            addParagraphToDocument(document, font, "\n??n situa??ia ??n care se primesc reclama??ii privind activitatea de colectare, Operatorul delegat include ??n raport ??nregistr??ri ale acestora. \n");

            addParagraphToDocument(document, boldFont, "\nAlte probleme\n");
            addParagraphToDocument(document, font, "- ??nregistr??ri accidente de munc??, situa??ii de urgen????;\n" +
                    "- Servicii neprogramate suplimentare;\n" +
                    "- Orice alte rapoarte ??i procese verbale solicitate de ADI Salubris Alba.\n\n");

            addParagraphToDocument(document, boldFont, "\n" +
                    "Anexe\n");

            addParagraphToDocument(document, font, "Operatorul anexeaz?? raportului  trimestrial urm??toarele documente: \n" +
                    "Fi??e de ??nc??rcare/desc??rcare;\n" +
                    "Bonurile de c??ntar;\n" +
                    "Graficul de colectare.\n" +
                    "\n" +
                    "\n" +
                    "       Director,                                                                                               ??ntocmit,\n" +
                    "                                                                                                                  \n");
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

    @Deprecated
    public ByteArrayInputStream generateMonthlyPdfReport(Date date) {
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

        LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        List<DailyReportDto> dailyReportDtos = dailyReportService.getAllReportsOfMonth(localDate.getMonthValue(), localDate.getYear()).stream()
                .map(DailyReportEntityToDailyReportDtoMapper::mapEntityToDto)
                .collect(Collectors.toList());
        MonthlyReportData monthlyReportData = new MonthlyReportData(dailyReportDtos, date.getMonth(), date.getYear());

        MonthlyReportTables monthlyReportTables = new MonthlyReportTables();

        try {
            addParagraphToDocument(document, boldFont, "ROMANIA");
            addParagraphToDocument(document, boldFont, "JUDETUL ALBA");
            addParagraphToDocument(document, boldFont, "ASOCIATIA DE DEZVOLTARE INTERCOMUNITARA SALUBRIS ALBA");
            addParagraphToDocument(document, boldFont, "Alba Iulia P-ta Ion I.C. Bratianu, nr. 1 cod postal 510118");
            addParagraphToDocument(document, boldFont, "email: albaadisalubris@gmail.com");
            addParagraphToDocument(document, boldFont, "\n\n\n");
            addParagraphToDocument(document, boldFont, "RAPORT LUNAR DE MONITORIZARE");
            addParagraphToDocument(document, boldFont, "\n\n\n");


            monthlyReportTables.createHeaderTable1(document, font, boldFont, "Mai 2022", monthlyReportData);

            addParagraphToDocument(document, boldFont, "\n\n\n");
            addParagraphToDocument(document, boldFont, "Date cuprinse in raportul lunar Ianuarie 2022 transmis de operatorul delegat\n");
            addParagraphToDocument(document, boldFont, "1. Cantitati de deseuri munincipale colectate\n");
            addParagraphToDocument(document, boldFont, "1.1. Deseuri reziduale colectate\n\n ");
            addParagraphToDocument(document, boldFont, "1.1.1. Mediul urban:\n\n ");

            monthlyReportTables.createUrbanResidualGarbageTable2(document, font, boldFont, monthlyReportData);

            addParagraphToDocument(document, boldFont, "1.1.1. Mediul rural:\n\n ");
            monthlyReportTables.createRuralResidualGarbageTable3(document, font, boldFont, monthlyReportData);

            addParagraphToDocument(document, boldFont, "1.2. Deseuri reciclabile colectate: \n\n ");
            addParagraphToDocument(document, boldFont, "1.2.1. Mediul urban: \n\n ");
            monthlyReportTables.createUrbanRecyclableGarbageTable4(document, font, boldFont, monthlyReportData);

            addParagraphToDocument(document, boldFont, "1.2.2. Mediul rural:\n\n ");
            monthlyReportTables.createRuralRecyclableGarbageTable5(document, font, boldFont, monthlyReportData);

            addParagraphToDocument(document, boldFont, "1.3. Alte tipuri de deseuri colectate:\n\n ");
            addParagraphToDocument(document, boldFont, "2. Cantitati de deseuri munincipale colectate si predate la instalatii\n\n ");
            addParagraphToDocument(document, boldFont, "2.1. Deseuri predate statiei de transfer Blaj\n\n ");

            monthlyReportTables.createTransferStationSubmittedGarbageTable6(document, font, boldFont, monthlyReportData);

            addParagraphToDocument(document, font, "Observatie\n");
            addParagraphToDocument(document, font, "Toate deseurile reciclabile colectate separat au fost dirijate direct la Statia de sortare din cadrul CMID Galda de Jos, fara sa fie predate statiei de sortare Blaj\n\n");
            addParagraphToDocument(document, boldFont, "2.2. Deseuri predate statiei de sortare\n");
            addParagraphToDocument(document, boldFont, "Cantitatea de deseuri reciclabile colectate separat predate direct Statiei de sortare din cadrul CMID Galda de Jos este de %s tone \n\n");

            addParagraphToDocument(document, boldFont, "2.3. Deseuri predate statiei de tratare mecano-biologica\n");
            addParagraphToDocument(document, font, "2.3. Cantitatea de deseuri reziduale colectate separat predate direct Statiei TMB din cadrul CMID Galda de Jos este de %s tone.\n\n");
            addParagraphToDocument(document, boldFont, "2.4. Deseuri predate la depozit.\n\n");


            monthlyReportTables.createDepositSubmittedGarbageTable7(document, font, boldFont, monthlyReportData);

            addParagraphToDocument(document, boldFont, "Observatie\n");
            addParagraphToDocument(document, font, "Nu a fost respectat fluxul deseurilor, o parte din deseurile colectate fiind dirijate direct la CMID Galda de Jos , fara sa fie predate statiei de transfer Blaj.\n\n");

            addParagraphToDocument(document, boldFont, "Reclamatii, sesizari\n");
            addParagraphToDocument(document, font, "Nu au fost raportate. Nu a fost prezentat registrul de inregistrare a reclamatiilor/sesizarilor. Pe site-ul operatorului nu exista portal separat dedicat sesizarilor utilizatorilor privind serviciul prestat.\n\n");

            addParagraphToDocument(document, boldFont, "Situatia contractarii si dotarii cu recipienti.\n");
            addParagraphToDocument(document, font, "Nu a fost raportata.\n\n");

        } catch (DocumentException e) {
            System.out.println(e);
        }
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }


    private void addParagraphToDocument(Document document, Font font, String phrase) throws DocumentException {
        document.add(new Paragraph(phrase, font));
    }

    public ByteArrayInputStream generateMonthlyPdfReports(Date date) {
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

        LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        List<DailyReportDto> dailyReportDtos = dailyReportService.getAllReportsOfMonth(localDate.getMonthValue(), localDate.getYear()).stream()
                .map(DailyReportEntityToDailyReportDtoMapper::mapEntityToDto)
                .collect(Collectors.toList());
        MonthlyReportData monthlyReportData = new MonthlyReportData(dailyReportDtos, date.getMonth(), date.getYear());

        MonthlyReportTables monthlyReportTables = new MonthlyReportTables();


        try {
            addParagraphToDocument(document, boldFont, "SC GREENDAYS SRL\t\n" +
                    "BAIA MARE B-UL TRAIAN NR.22A\t\n" +
                    "J24/1197/2006\t\n" +
                    "CUI 18903400\t\n" +
                    "PUNCT DE LUCRU BLAJ\t\n" +
                    "\t\n" +
                    "\t\n" +
                    "\t\n" +
                    "\t\n" +
                    " Tabel 1 : Tabel centralizator deseuri colectate\t\n" +
                    "\t");


            monthlyReportTables.createCentralisingTable1(document, boldFont, font, monthlyReportData);
            monthlyReportTables.createResidualTable2(document, boldFont, font, monthlyReportData);
            monthlyReportTables.createRecyclableTable3(document, boldFont, font, monthlyReportData);
            monthlyReportTables.createOtherTypesTable4(document, boldFont, font, monthlyReportData);
            monthlyReportTables.createTransferStationTable5(document, boldFont, font, monthlyReportData);
            monthlyReportTables.createSortingStationTable6(document, boldFont, font, monthlyReportData);
            monthlyReportTables.createDepositTable7(document, boldFont, font, monthlyReportData);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }


}