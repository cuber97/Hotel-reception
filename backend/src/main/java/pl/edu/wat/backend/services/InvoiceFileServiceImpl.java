package pl.edu.wat.backend.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.ReservationDto;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class InvoiceFileServiceImpl implements InvoiceFileService{
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public void generateDocument(OutputStream outputStream, ReservationDto reservationDto) {
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            addMetaData(document);
            addTitlePage(document, reservationDto);
            document.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Faktura");
        document.addAuthor("Recepcja hotelowa");
        document.addCreator("Recepcja hotelowa");
    }

    private static void addTitlePage(Document document,ReservationDto reservationDto)
            throws DocumentException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Paragraph preface = new Paragraph();

        addEmptyLine(preface, 1);

        String documentDate = simpleDateFormat.format(new Date());

        Paragraph date = new Paragraph(documentDate, redFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        preface.add(date);

        addEmptyLine(preface, 4);

        Paragraph title = new Paragraph("Faktura nr 2", smallBold);
        title.setAlignment(Element.ALIGN_CENTER);
        preface.add(title);

        addEmptyLine(preface, 4);

        String buyerName = reservationDto.getCustomer().getFirstName() + ' ' + reservationDto.getCustomer().getLastName();

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.addCell(getCell("Sprzedawca:", PdfPCell.ALIGN_LEFT));
        headerTable.addCell(getCell("Nabywca:", PdfPCell.ALIGN_RIGHT));
        headerTable.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
        headerTable.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
        headerTable.addCell(getCell("Hotel", PdfPCell.ALIGN_LEFT));
        headerTable.addCell(getCell(buyerName, PdfPCell.ALIGN_RIGHT));
        headerTable.addCell(getCell("hotelarz@gmail.com", PdfPCell.ALIGN_LEFT));
        headerTable.addCell(getCell(reservationDto.getCustomer().getEmail(), PdfPCell.ALIGN_RIGHT));
        headerTable.addCell(getCell("515515515", PdfPCell.ALIGN_LEFT));
        headerTable.addCell(getCell(reservationDto.getCustomer().getPhoneNumber(), PdfPCell.ALIGN_RIGHT));
        preface.add(headerTable);

        addEmptyLine(preface, 4);

        PdfPTable detailTable = new PdfPTable(9);
        detailTable.setWidthPercentage(100);
        detailTable.setWidths(new float[] {1, 5, 4, 4, 3, 3, 3, 3, 3});

        PdfPCell cell = new PdfPCell(new Phrase("Lp"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Numer rezerwacji"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Data od"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Data do"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Cena netto"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Stawka VAT"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Wartosc netto"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Wartosc VAT"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Wartosc brutto"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailTable.addCell(cell);

        detailTable.setHeaderRows(1);

        DecimalFormat df = new DecimalFormat("###.00");

        detailTable.addCell(getCell2("1"));
        detailTable.addCell(getCell2(reservationDto.getReservationId().toString()));
        detailTable.addCell(getCell2(simpleDateFormat.format(reservationDto.getDateFrom())));
        detailTable.addCell(getCell2(simpleDateFormat.format(reservationDto.getDateTo())));
        detailTable.addCell(getCell2(df.format(((reservationDto.getPrice()).multiply(new BigDecimal("0.77"))))));
        detailTable.addCell(getCell2("23%"));
        detailTable.addCell(getCell2(df.format((((reservationDto.getPrice())).multiply(new BigDecimal("0.77"))))));
        detailTable.addCell(getCell2(df.format((((reservationDto.getPrice()))).multiply(new BigDecimal("0.23")))));
        detailTable.addCell(getCell2(df.format((reservationDto.getPrice()))));

        preface.add(detailTable);

        addEmptyLine(preface, 4);

        preface.add(getParagraph("Razem: " + (reservationDto.getPrice()) + " " + "PLN"));

        document.add(preface);

        document.newPage();
    }

    private static Paragraph getParagraph(String text){
        Paragraph paragraph = new Paragraph(text);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        return paragraph;
    }

    private static PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private static PdfPCell getCell2(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPaddingTop(2);
        cell.setPaddingBottom(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
