package org.BalajiStore.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;

import org.BalajiStore.Dto.ItemReportDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportPdfService {

    public byte[] generatePdf(List<ItemReportDto> reports) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Balaji Store Report"));

            Table table = new Table(5);

            table.addCell("Item");
            table.addCell("Opening Stock");
            table.addCell("Purchased");
            table.addCell("Used");
            table.addCell("Closing Stock");

            for (ItemReportDto r : reports) {

                table.addCell(r.getItemName());
                table.addCell(String.valueOf(r.getOpeningStock()));
                table.addCell(String.valueOf(r.getPurchased()));
                table.addCell(String.valueOf(r.getUsed()));
                table.addCell(String.valueOf(r.getClosingStock()));
            }

            document.add(table);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}