package com.greendays.greendays.report;

import com.greendays.greendays.report.tables.TrimestrialReportTables;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfReportGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfReportGenerator.class.getName());
    public static final String FONT = "src/main/resources/FreeSans.ttf";
    public static final String FONT_BOLD = "src/main/resources/FreeSansBold.ttf";


    public ByteArrayInputStream generateTrimestrialPdfReport() {
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

        TrimestrialReportTables trimestrialReportTables = new TrimestrialReportTables();
        try {

            document.add(new Paragraph("Raport trimestrial\n \n" +
                    "Perioada de raportare: 01.12.2021 – 28.02.2022\n", boldFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            trimestrialReportTables.createFirstReportTable1(document, boldFont, font);

            addParagraphToDocument(document, font, "\n");
            addParagraphToDocument(document, boldFont, "Secțiunea I – Aspecte tehnice\n" +
                    "Capitolul 1. Cantități de deșeuri municipale colectate\n");
            addParagraphToDocument(document, font, "Operatorul delegat prezintă un tabel centralizator privind cantitățile de deșeuri municipale colectate din aria delegării și defalcată pe surse (beneficiari casnici și non-casnici) pentru trimestrul aferent raportului. ");
            addParagraphToDocument(document, boldFont, "Tabel 1: Tabel centralizator deșeuri colectate\n\n\n");

            trimestrialReportTables.createCentralisingTable2(document, boldFont, font);
            addParagraphToDocument(document, font, "\n");
            addParagraphToDocument(document, font, "Operatorul delegat prezintă tabele privind cantitățile de deșeuri colectate, pe tipuri de deșeuri, pentru trimestrul aferent raportului:\n");
            addParagraphToDocument(document, boldFont, "1.1. Deșeuri reziduale colectate\n" +
                    "1.1.1. Mediul urban:\n" +
                    "Tabel 2: Deșeuri reziduale colectate\n");
            trimestrialReportTables.createUrbanEnvTable3(document, boldFont, font);
            addParagraphToDocument(document, font, "Notă:\t\t\n" +
                    "* TMB, depozitare\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "1.1.2. Mediul rural:\n" +
                    "Tabel 3: Deșeuri reziduale colectate \n");
            trimestrialReportTables.createRuralEnvTable4(document, boldFont, font);
            addParagraphToDocument(document, boldFont, "Notă:\t\t\n" +
                    "* TMB, depozitare\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "       1.2. Deșeuri reciclabile colectate\n" +
                    "1.2.1. Mediul urban: \n" +
                    "Tabel 4: Deșeuri reciclabile colectate separat\n");
            trimestrialReportTables.createRecyclableUrbanTable5(document, boldFont, font);
            addParagraphToDocument(document, boldFont, "Notă:\n" +
                    "* stație sortare, depozitare\n" +
                    "\n" +
                    "1.2.2. Mediul rural: \n" +
                    "Tabel 5: Deșeuri reciclabile colectate separat\n");
            trimestrialReportTables.createRecyclableRuralTable6(document, boldFont, font);
            addParagraphToDocument(document, boldFont, "Notă:\n" +
                    "* stație sortare, depozitare\n" +
                    "1.3. Alte tipuri de deșeuri colectate\n" +
                    "1.3.1. Mediul urban: \n" +
                    "\n" +
                    "Tabel 6: Alte tipuri de deșeuri colectate\n");
            trimestrialReportTables.createUrbanOtherTypesOfGarbageTable7(document, boldFont, font);
            addParagraphToDocument(document, boldFont, "Notă:\n" +
                    "* CMID Galda de Jos\n" +
                    "1.3.2. Mediul rural: \n" +
                    "\n" +
                    "Tabel 7: Alte tipuri de deșeuri colectate\n");
            trimestrialReportTables.createRuralOtherTypesOfGarbageTable8(document, boldFont, font);
            addParagraphToDocument(document, boldFont, "\n" +
                    "  Notă:\n" +
                    "* CMID Galda de Jos\n" +
                    "Capitolul 2. Cantități de deșeuri municipale colectate și predate altor operatori/instalații\n" +
                    "Operatorul prezintă un tabel centralizator privind cantitățile de deșeuri municipale colectate și predate altor operatori/instalații.\n" +
                    "\n" +
                    "Tabel 8: Deșeuri predate stației de sortare\n" +
                    "\n");
            trimestrialReportTables.createSortingStationTable9(document, boldFont, font);
            addParagraphToDocument(document, boldFont, "\nTabel 9: Deșeuri predate stației de tratare mecano-biologică\n");
            trimestrialReportTables.createBioTreatmentTable10(document, boldFont, font);
            addParagraphToDocument(document, boldFont, "\nTabel 10: Deșeuri predate depozitului de deșeuri\n");
            trimestrialReportTables.createGarbageSubmittedToDepositTable11(document, boldFont, font);
            addParagraphToDocument(document, font, "\nOperatorul prezintă situația cantităților de deșeuri colectate din locuri neamenajate/nepermise, precum și a deșeurilor respinse la colectare și cauzele respingerii.\n" +
                    "Operatorul prezintă o listă a tuturor beneficiarilor cu care au un contract de prestări de servicii încheiat.\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "\nCapitolul 3. Indicatori de performanță\n");
            addParagraphToDocument(document, font, "Delegatul va raporta trimestrial Delegatarului (ADI Salubris Alba) performanța realizată în ceea ce privește respectarea indicatorilor de performanță stabiliți pe o bază trimestrială, după caz, precum și modul de îndeplinire a programului de investiții asumat prin contractul de delegare.\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "Secțiunea II – Aspecte financiare     \n");
            addParagraphToDocument(document, boldFont, "\nCapitolul 4. Sume calculate și plătite pentru operațiunile de tratare/eliminare a deșeurilor\n");
            addParagraphToDocument(document, font, "\nOperatorul delegat va raporta trimestrial Delegatarului (ADI Salubris Alba), următoarele documente:\n" +
                    "1. Contractele încheiate cu operatorul instalațiilor;\n" +
                    "2. Dovada plății tarifelor pentru activitățile de tratare a deșeurilor.\n" +
                    "\n" +
                    "\n");
            addParagraphToDocument(document, boldFont, "\n" +
                    "\n" +
                    "Capitolul 5. Redevența și taxa de administrare\n" +
                    "        1. Redevența\n" +
                    "   \n\n   Tabel 10: Calculul și plata redevenței datorate UAT-urilor pentru trimestrul ...\n");
            trimestrialReportTables.createTrimestrialUatPaymentTable13(document, boldFont, font);

            addParagraphToDocument(document, boldFont, "        2. Taxa de administrare \n" +
                    "       Tabel 11: Calculul și plata taxei de administrare datorată către ADI Salubris Alba pentru trimestrul ...\n");

            trimestrialReportTables.createAdiSalubrisPaymentTable12(document, boldFont, font);

            addParagraphToDocument(document, font, "\n" +
                    "       Observații:   (dacă este cazul).\n");

            addParagraphToDocument(document, boldFont, "\nCapitolul. 6 Probleme întâmpinate în prestarea serviciului\n" +
                    "Întreruperea activității de colectare\n");
            addParagraphToDocument(document, font, "În situația în care apar întreruperi ale activității de colectare în trimestrul aferent raportării, Operatorul anexează următoarele:\n" +
                    "Înregistrări ale problemelor întâmpinate în ziua respectivă: \n" +
                    "întreruperi programate și/sau neprogramate;\n" +
                    "defecțiuni și accidente;\n" +
                    "timpul de oprire al autospecialelor sau unor subansamble ale acestora;\n" +
                    "natura fiecărei defecțiuni;\n" +
                    "activitățile de reparație/întreținere;\n" +
                    "înlocuiri ale vehiculelor, utilajelor, containerelor sau personalului;\n" +
                    "condiții meteo, etc.\n" +
                    "Înregistrări ale lucrărilor de întreținere și reparații la autospeciale sau alte vehicule, puncte de      colectare și containere;  \n" +
                    "Rapoarte ale personalului desemnat pentru supervizare și monitorizare;\n" +
                    "Rapoarte ale conducătorilor de autovehicule și operatorilor de pe autovehicule: \n" +
                    "vehicule utilizate/neutilizate;\n" +
                    "motive pentru scoaterea din uz a vehiculelor;\n" +
                    "media sarcinilor utile;\n" +
                    "distanța parcursă etc.\n" +
                    "\n" +
                    "Starea containerelor și a punctelor de colectare\n" +
                    "Operatorul delegat prezintă o situație privind starea containerelor și a punctelor de colectare individuale și colective pentru fiecare comună, oraș și municipiu: \n" +
                    "Numărul și tipul containerelor plasate;\n" +
                    "Numărul containerelor reparate; \n" +
                    "Numărul containerelor înlocuite;\n" +
                    "Numărul containerelor furate, distruse și avariate; \n" +
                    "Numărul punctelor de colectare avariate și reparate;\n" +
                    "Numărul punctelor de colectare care au trebuit mai întâi să fie curățate înainte ca vehiculul să ajungă la containere.\n");

            addParagraphToDocument(document, boldFont, "Reclamații ");

            addParagraphToDocument(document, font, "\nÎn situația în care se primesc reclamații privind activitatea de colectare, Operatorul delegat include în raport înregistrări ale acestora. \n");

            addParagraphToDocument(document,boldFont,"\nAlte probleme\n");
            addParagraphToDocument(document,font,"Înregistrări accidente de muncă, situații de urgență;\n" +
                    "Servicii neprogramate suplimentare;\n" +
                    "Orice alte rapoarte și procese verbale solicitate de ADI Salubris Alba.\n");

            addParagraphToDocument(document,boldFont,"\n" +
                    "Anexe\n");

            addParagraphToDocument(document,font,"Operatorul anexează raportului  trimestrial următoarele documente: \n" +
                    "Fișe de încărcare/descărcare;\n" +
                    "Bonurile de cântar;\n" +
                    "Graficul de colectare.\n" +
                    "\n" +
                    "\n" +
                    "       Director,                                                                                               Întocmit,\n" +
                    "                                                                                                                  \n");
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

    private void addParagraphToDocument(Document document, Font font, String phrase) throws DocumentException {
        document.add(new Paragraph(phrase, font));
    }

}