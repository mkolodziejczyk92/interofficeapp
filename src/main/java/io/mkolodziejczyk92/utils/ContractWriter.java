package io.mkolodziejczyk92.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j
public class ContractWriter{


    public static void createPdfContract() {

        LocalDate currentDate = LocalDate.now();
        String contractNumber = "123456";
        String clientName = "Jan Kowalski";
        String clientAddress = "ul. Przykładowa 1, 00-001 Warszawa";
        String clientPhone = "123456789";
        String wykonawcaName = "INTERDOM Radosław Nastaj";
        String wykonawcaAddress = "ul. Husarska 8/36, 20-555 Lublin";
        String wykonawcaPhone = "502-288-648";
        String rodzajUslugi = "montaż drzwi";
        String adresInwestycji = "ul. Budowlana 2, 30-002 Kraków";
        String kwotaNetto = "1000";
        String kwotaNettoSlownie = "tysiąc";
        String wartoscVat = "230";
        String kwotaBrutto = "1230";
        String zaliczka = "500";
        String planowanaDataRealizacji = "2023-06-30";

        String fileName = "umowa.pdf";
        try {
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);

            Document document = new Document(PageSize.A4, 30, 30 ,30 ,30);

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
            document.add(createParagraphWithMiddleBoldText(boldFont, "UMOWA NR " + contractNumber ));
            document.add(EMPTY_LINE());

            // Strony umowy
            document.add(new Paragraph("zawarta dnia " + currentDate + " w Lublinie pomiędzy:", normalFont));
            document.add(new Paragraph(clientName, normalFont));
            document.add(new Paragraph(clientAddress, normalFont));
            document.add(new Paragraph(clientPhone, normalFont));

            document.add(EMPTY_LINE());
            document.add(new Paragraph("a", boldFont));
            document.add(EMPTY_LINE());

            document.add(new Paragraph(wykonawcaName, normalFont));
            document.add(new Paragraph(wykonawcaAddress, normalFont));
            document.add(new Paragraph(wykonawcaPhone, normalFont));

            document.add(EMPTY_LINE());

            // Treść umowy
            document.add(new Paragraph("o treści następującej:", normalFont));
            document.add(EMPTY_LINE());

            // §1

            document.add(createParagraphWithMiddleBoldText(boldFont, "§1"));
            document.add(new Paragraph("1. Zamawiający zleca a Wykonawca zobowiązuje się do wykonania usługi budowlanej / remontowo-budowlanej w rozumieniu art. 146 pkt. 1 ppkt 2 lit. a i b ustawy o podatku VAT polegającą na " + rodzajUslugi + " w budynku jednorodzinnym pod adresem: " + adresInwestycji + ". 1.  Rodzaje materiałów jakie zostaną zastosowane przez Wykonawcę, ich parametry użytkowe oraz zakres robót budowlanych przewidzianych do wykonania w ramach niniejszej umowy określa załącznik, w przypadku zmiany zakresu robót zostanie sporządzony aneks do niniejszej umowy wyszczególniający roboty dodatkowe i określający ich wartość.", normalFont));
            document.add(new Paragraph("2. Całość usługi zostanie wykonana z materiałów Wykonawcy.", normalFont));
            document.add(new Paragraph("3. Zamawiający zobowiązuje się do przygotowania otworów montażowych wg wytycznych wykonawcy", normalFont));
            document.add(new Paragraph("4. Zamawiający oświadcza, że zapoznał się z załącznikiem (ofertą) jest ona zgodna ze złożonym zamówieniem i akceptuje jej zapisy.", normalFont));
            document.add(new Paragraph("5. Zastosowane materiały wykonane będą zgodnie z normą EN 13 659 +A1, EN 12 216, EN 1070.", normalFont));
            document.add(new Paragraph("Na wykonanie elementów wykraczających parametrami poza zakres Dokumentacji Technicznej Zamawiający wyraża zgodę, a projektant / kierownik budowy / inspektor nadzoru inwestorskiego zatwierdza Zamawiającemu przyjęte przez Wykonawcę rozwiązania techniczne swoim podpisem na rysunkach wykonanych przez Wykonawcę.", normalFont));
            document.add(EMPTY_LINE());

            // §2
            document.add(createParagraphWithMiddleBoldText(boldFont, "§2"));
            document.add(new Paragraph("1. Strony ustaliły, że wynagrodzeniem Wykonawcy będzie kwota netto " + kwotaNetto + " (słownie złotych: " + kwotaNettoSlownie + ") powiększona o należny " + wartoscVat + " podatek VAT tj. łącznie " + kwotaBrutto + " brutto.", normalFont));
            document.add(new Paragraph("2. Wynagrodzenie będzie płatne w następujący sposób: w terminie do 3 dni roboczych od dnia podpisania niniejszej umowy Zamawiający wpłaci zaliczkę na konto Wykonawcy mBank 23 1140 2004 0000 3802 7954 9774 lub gotówką w siedzibie firmy w kwocie równej " + zaliczka + " wraz z należnym podatkiem VAT. Na kwotę zaliczki Wykonawca wystawi fakturę VAT. Pozostała wartość kwoty umownej tj. " + kwotaBrutto + " - zaliczka, pole powinno przeliczać się samo Zamawiający wpłaci w dniu zakończenia robót budowlanych na podstawie faktury VAT.", normalFont));
            document.add(new Paragraph("3. Opóźnienie w dokonaniu płatności może skutkować zmianą terminu wykonania.", normalFont));
            document.add(EMPTY_LINE());

            // §3
            document.add(createParagraphWithMiddleBoldText(boldFont, "§3"));
            document.add(new Paragraph("W przypadku odstąpienia od umowy przez Zamawiającego traci on wpłaconą zaliczkę, a jeżeli przedmiot umowy wymagał użycia materiałów nietypowych, które zostały zamówione przez Wykonawcę u dostawców, zobowiązuje się do zapłaty ich wartości.", normalFont));
            document.add(EMPTY_LINE());

            // §4
            document.add(createParagraphWithMiddleBoldText(boldFont, "§4"));
            document.add(new Paragraph("1. Wykonawca przystąpi do realizacji umowy w dniu uznania zaliczki na rachunku bankowym.", normalFont));
            document.add(new Paragraph("2. Termin rozpoczęcia prac budowlanych, określa się na " + planowanaDataRealizacji + ".", normalFont));
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
            table.setWidthPercentage(100); // Ustawienie szerokości tabeli na 100% szerokości strony

            PdfPCell zamawiajacyCell = new PdfPCell();
            zamawiajacyCell.addElement(new Paragraph("ZAMAWIAJĄCY"));
            zamawiajacyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            zamawiajacyCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell wykonawcaCell = new PdfPCell();
            Paragraph paragraph = new Paragraph("WYKONAWCA");
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            wykonawcaCell.addElement(paragraph);
            wykonawcaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            wykonawcaCell.setBorder(Rectangle.NO_BORDER);

            table.addCell(zamawiajacyCell);
            table.addCell(wykonawcaCell);

            document.add(table);

            document.close();

        } catch (FileNotFoundException | DocumentException e) {
            log.info(e.getMessage());
        } catch (IOException e) {
            log.info("Font not found");
            log.info(e.getMessage());
        }
    }

    private static PdfPTable createParagraphWithMiddleBoldText(Font font, String text){
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

        return  table;

    }
    private static Paragraph EMPTY_LINE() {
        return new Paragraph(" ");
    }
}