package io.mkolodziejczyk92.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
@Slf4j
public class ContractWriter {

    // pogrubiona czcionka
    private static final Font FONT_TIMES_ROMAN_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    // normalna czcionka
    private static final Font FONT_TIMES_ROMAN_NORMAL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    // tworzy pustą linie
    private static final Paragraph EMPTY_LINE = new Paragraph("\n");

    public static void main(String[] args)  {

        Document document = new Document();
        try {
            // tutaj zamiast create.pdf damy contract number, będzie tworzyć za każdym razem nowy plik
            PdfWriter.getInstance(document, new FileOutputStream("E:\\create.pdf"));

            document.open();

            document.addTitle("Contract");
            document.addAuthor("INTERDOM");
            document.addSubject("Contract agreement");
            document.addKeywords("Contract, agreement, Sale, INTERDOM");


            Paragraph contractNumberParagraph =
                    new Paragraph("Umowa - Zamówienie numer" + " tutaj przekazujemy contract number" + " zawarta w dniu"
                            + " tutaj damy localdate" + " w " + " tutaj idzie miejscowosc" + " pomiedzy Zleceniobiorca INTERDOM" +
                            " , a Zleceniodawca" + " tutaj zaciągamy dane o kliencie", FONT_TIMES_ROMAN_BOLD);
            // pozwala ustalić wielkość wcięcia akapitowego w pierwszej linijce
            contractNumberParagraph.setFirstLineIndent(50);
            // pozwala ustawić aby paragraf był przesunięty do lewej strony
            contractNumberParagraph.setAlignment(Element.ALIGN_LEFT);
            Paragraph contractNumberParagrap1=
                    new Paragraph("Umowa - Zamówienie numer" + " tutaj przekazujemy contract number" + " zawarta w dniu"
                            + " tutaj damy localdate" + " w " + " tutaj idzie miejscowosc" + " pomiedzy Zleceniobiorca INTERDOM" +
                            " , a Zleceniodawca" + " tutaj zaciągamy dane o kliencie", FONT_TIMES_ROMAN_NORMAL);
            document.add(contractNumberParagraph);
            document.add(EMPTY_LINE);
            document.add(contractNumberParagrap1);

            document.close();


           log.info("Dokument został utworzony");
        }catch (FileNotFoundException e){
            log.error("File not found exception ");
        } catch (DocumentException e){
            log.error("Document exception");
        }

    }


}
