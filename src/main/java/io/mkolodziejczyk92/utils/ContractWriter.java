package io.mkolodziejczyk92.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;


@Slf4j
public class ContractWriter {

    private ContractWriter() {
    }

    public static void createPdfContract(String clientPhone, String contractNumber, String clientFullName, String clientInvestmentAddress,
                                         String commodityType, String clientAddress, String plannedImplementationDate, String signatureDate, String netAmount, String wholePartNetAmountInWords, String fractionalPartNetAmountInWords,
                                         String grossAmount) {


        LocalDate currentDate = LocalDate.now();
        String contractorName = "INTERDOM Radosław Nastaj";
        String contractorAddress = "ul. Husarska 8/36, 20-555 Lublin";
        String contractorPhone = "502-288-648";
        String fileName = "umowa.pdf";


        String wartoscVat = "230";
        String zaliczka = "500";

        try {
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);

            Document document = new Document(PageSize.A4, 30, 30, 30, 30);

            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Wybierz miejsce zapisu");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File f) {
                        return f.getName().toLowerCase().endsWith(".pdf") || f.isDirectory();
                    }

                    public String getDescription() {
                        return "Pliki PDF (*.pdf)";
                    }
                });

                int result = fileChooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Dodaj rozszerzenie .pdf, jeśli nie jest już w nazwie pliku
                    String filePath = selectedFile.getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".pdf")) {
                        filePath += ".pdf";
                    }

                    PdfWriter.getInstance(document, new FileOutputStream(filePath));

                    log.info("Dokument został utworzony");
                }
            } catch (FileNotFoundException e) {
                log.error("File not found exception ");
            } catch (DocumentException e) {
                log.error("Document exception");
            }



            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();



            // Nagłówek
            Paragraph header = new Paragraph("Lublin, " + currentDate, normalFont);
            header.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(header);
            document.add(createParagraphWithMiddleBoldText(boldFont, "UMOWA NR " + contractNumber));
            document.add(EMPTY_LINE());

            // Strony umowy
            document.add(new Paragraph("zawarta dnia " + signatureDate + " w Lublinie pomiędzy:", normalFont));

            document.add(EMPTY_LINE());
            document.add(new Paragraph(clientFullName, normalFont));
            document.add(new Paragraph(clientAddress, normalFont));
            document.add(new Paragraph("Numer Telefonu: " + clientPhone, normalFont));

            document.add(EMPTY_LINE());
            document.add(new Paragraph("a", boldFont));
            document.add(EMPTY_LINE());

            document.add(new Paragraph(contractorName, normalFont));
            document.add(new Paragraph(contractorAddress, normalFont));
            document.add(new Paragraph("Numer Telefonu: " + contractorPhone, normalFont));

            document.add(EMPTY_LINE());

            // Treść umowy
            document.add(new Paragraph("o treści następującej:", normalFont));
            document.add(EMPTY_LINE());

            // §1

            document.add(createParagraphWithMiddleBoldText(boldFont, "§1"));
            document.add(new Paragraph("1. Zamawiający zleca a Wykonawca zobowiązuje się do wykonania usługi budowlanej / remontowo-budowlanej w rozumieniu art. 146 pkt. 1 ppkt 2 lit. a i b ustawy o podatku VAT polegającą na " + commodityType + " w budynku jednorodzinnym pod adresem: \n\n" + clientInvestmentAddress + ".\n\n 1.  Rodzaje materiałów jakie zostaną zastosowane przez Wykonawcę, ich parametry użytkowe oraz zakres robót budowlanych przewidzianych do wykonania w ramach niniejszej umowy określa załącznik, w przypadku zmiany zakresu robót zostanie sporządzony aneks do niniejszej umowy wyszczególniający roboty dodatkowe i określający ich wartość.", normalFont));
            document.add(new Paragraph("2. Całość usługi zostanie wykonana z materiałów Wykonawcy.", normalFont));
            document.add(new Paragraph("3. Zamawiający zobowiązuje się do przygotowania otworów montażowych wg wytycznych wykonawcy", normalFont));
            document.add(new Paragraph("4. Zamawiający oświadcza, że zapoznał się z załącznikiem (ofertą) jest ona zgodna ze złożonym zamówieniem i akceptuje jej zapisy.", normalFont));
            document.add(new Paragraph("5. Zastosowane materiały wykonane będą zgodnie z normą EN 13 659 +A1, EN 12 216, EN 1070.", normalFont));
            document.add(new Paragraph("Na wykonanie elementów wykraczających parametrami poza zakres Dokumentacji Technicznej Zamawiający wyraża zgodę, a projektant / kierownik budowy / inspektor nadzoru inwestorskiego zatwierdza Zamawiającemu przyjęte przez Wykonawcę rozwiązania techniczne swoim podpisem na rysunkach wykonanych przez Wykonawcę.", normalFont));
            document.add(EMPTY_LINE());

            // §2
            document.add(createParagraphWithMiddleBoldText(boldFont, "§2"));
            document.add(new Paragraph("1. Strony ustaliły, że wynagrodzeniem Wykonawcy będzie kwota netto " + netAmount + " (słownie złotych: " + wholePartNetAmountInWords + " złotych oraz " + fractionalPartNetAmountInWords + " groszy) powiększona o należny " + wartoscVat + " podatek VAT tj. łącznie " + grossAmount + " brutto.", normalFont));
            document.add(new Paragraph("2. Wynagrodzenie będzie płatne w następujący sposób: w terminie do 3 dni roboczych od dnia podpisania niniejszej umowy Zamawiający wpłaci zaliczkę na konto Wykonawcy mBank 23 1140 2004 0000 3802 7954 9774 lub gotówką w siedzibie firmy w kwocie równej " + zaliczka + " wraz z należnym podatkiem VAT. Na kwotę zaliczki Wykonawca wystawi fakturę VAT. Pozostała wartość kwoty umownej tj. " + grossAmount + " - zaliczka, pole powinno przeliczać się samo Zamawiający wpłaci w dniu zakończenia robót budowlanych na podstawie faktury VAT.", normalFont));
            document.add(new Paragraph("3. Opóźnienie w dokonaniu płatności może skutkować zmianą terminu wykonania.", normalFont));
            document.add(EMPTY_LINE());

            // §3
            document.add(createParagraphWithMiddleBoldText(boldFont, "§3"));
            document.add(new Paragraph("W przypadku odstąpienia od umowy przez Zamawiającego traci on wpłaconą zaliczkę, a jeżeli przedmiot umowy wymagał użycia materiałów nietypowych, które zostały zamówione przez Wykonawcę u dostawców, zobowiązuje się do zapłaty ich wartości.", normalFont));
            document.add(EMPTY_LINE());

            // §4
            document.add(createParagraphWithMiddleBoldText(boldFont, "§4"));
            document.add(new Paragraph("1. Wykonawca przystąpi do realizacji umowy w dniu uznania zaliczki na rachunku bankowym.", normalFont));
            document.add(new Paragraph("2. Termin rozpoczęcia prac budowlanych, określa się na " + plannedImplementationDate + ".", normalFont));
            document.add(new Paragraph("3. Termin zakończenia prac budowlanych nastąpi w ciągu 14 dni od rozpoczęcia prac budowlanych.", normalFont));
            document.add(new Paragraph("4. Wykonawca zastrzega, że termin montażu może ulec zmianie ze względu na niesprzyjające warunki pogodowe.", normalFont));
            document.add(new Paragraph("5. Termin odbioru końcowego robót budowlanych określi Wykonawca w porozumieniu z Zamawiającym.", normalFont));
            document.add(new Paragraph("6. Jeżeli Zamawiający nie zgłosi się w uzgodnionym terminie celem dokonania odbioru robót, Wykonawca ma prawo dokonać odbioru jednostronnie.", normalFont));
            document.add(new Paragraph("7. W przypadku stwierdzenia wad, niezgodności lub niekompletności wykonanych robót budowlanych z załącznikiem nr 1, Strony ustalą nowy termin przystąpienia do odbioru, w którym Wykonawca będzie zobowiązany usunąć wady bądź niezgodności.", normalFont));
            document.add(new Paragraph("8. W przypadku opóźnień w wykonaniu robót z przyczyn niezawinionych przez Wykonawcę, przyjmuje się, że termin wykonania został dotrzymany.", normalFont));
            document.add(EMPTY_LINE());

            // §5
            document.add(createParagraphWithMiddleBoldText(boldFont, "§5"));
            document.add(new Paragraph("Prace montażowe wykonane zostaną zgodnie z instrukcją ich Producenta, a w kwestiach nieokreślonych niniejszą umową zgodnie z obowiązującymi przepisami prawa budowlanego, a w szczególności zgodnie z art. 647 i 649 Kodeksu cywilnego.", normalFont));
            document.add(EMPTY_LINE());

            // §6
            document.add(createParagraphWithMiddleBoldText(boldFont, "§6"));
            document.add(new Paragraph("Wszelkie zmiany niniejszej umowy, pod rygorem nieważności, wymagają formy pisemnego \taneksu.", normalFont));
            document.add(EMPTY_LINE());

            // §7
            document.add(createParagraphWithMiddleBoldText(boldFont, "§7"));
            document.add(new Paragraph("Zamawiający wyraża zgodę na wykorzystanie  dokumentacji zdjęciowej  do celów marketingowych INTERDOM Radosław Nastaj", normalFont));
            document.add(EMPTY_LINE());

            // §8
            document.add(createParagraphWithMiddleBoldText(boldFont, "§8"));
            document.add(new Paragraph("W sprawach nieuregulowanych niniejszą umową zastosowanie mają przepisy Kodeksu Cywilnego.", normalFont));
            document.add(EMPTY_LINE());

            // §9
            document.add(createParagraphWithMiddleBoldText(boldFont, "§9"));
            document.add(new Paragraph("Umowa została sporządzona w dwóch jednobrzmiących egzemplarzach, po jednym dla \tkażdej ze stron.", normalFont));
            document.add(EMPTY_LINE());

            // Podpisy
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            PdfPCell orderingCell = new PdfPCell();
            orderingCell.addElement(new Paragraph("ZAMAWIAJĄCY"));
            orderingCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            orderingCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell contractorCell = new PdfPCell();
            Paragraph paragraph = new Paragraph("WYKONAWCA");
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            contractorCell.addElement(paragraph);
            contractorCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            contractorCell.setBorder(Rectangle.NO_BORDER);

            table.addCell(orderingCell);
            table.addCell(contractorCell);

            document.add(table);

            document.close();

        } catch (FileNotFoundException | DocumentException e) {
            log.info(e.getMessage());
        } catch (IOException e) {
            log.info("Font not found");
            log.info(e.getMessage());
        }
    }

    private static PdfPTable createParagraphWithMiddleBoldText(Font font, String text) {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_MIDDLE);


        Paragraph section = new Paragraph(text, font);
        section.setAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell = new PdfPCell(section);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        return table;

    }

    private static Paragraph EMPTY_LINE() {
        return new Paragraph(" ");
    }

    static class HeaderFooterPageEvent extends PdfPageEventHelper {

        Font font = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase header = new Phrase("this is a header", font);
            Phrase footer = new Phrase("this is a footer", font);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }


    }
}
