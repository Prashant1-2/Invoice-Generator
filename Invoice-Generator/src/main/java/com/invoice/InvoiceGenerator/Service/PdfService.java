package com.invoice.InvoiceGenerator.Service;

import com.invoice.InvoiceGenerator.Model.InvoiceItem;
import com.invoice.InvoiceGenerator.Model.InvoiceRequest;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfService {

    public byte[] generateInvoicePdf(InvoiceRequest invoice) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("From: " + invoice.getBusinessName(), normal));
            document.add(new Paragraph("To: " + invoice.getClientName(), normal));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 1, 2, 2});

            Stream.of("Description", "Qty", "Rate", "Amount").forEach(header -> {
                PdfPCell cell = new PdfPCell();
                cell.setPhrase(new Phrase(header, bold));
                table.addCell(cell);
            });

            List<InvoiceItem> items = invoice.getItems();
            double grandTotal = 0;
            for (InvoiceItem item : items) {
                table.addCell(new Phrase(item.getDescription(), normal));
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.format("%.2f", item.getRate()));
                double total = item.getTotal();
                table.addCell(String.format("%.2f", total));
                grandTotal += total;
            }

            PdfPCell totalCell = new PdfPCell(new Phrase("Total", bold));
            totalCell.setColspan(3);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(totalCell);
            table.addCell(new Phrase(String.format("%.2f", grandTotal), bold));

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }

        return out.toByteArray();
    }
}
