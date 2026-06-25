package org.BalajiStore.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.BalajiStore.Dto.ItemReportDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
public class ReportPdfService {

    private String formatRs(double value) {

        NumberFormat nf =
                NumberFormat.getNumberInstance(
                        new Locale("en","IN")
                );

        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        return "Rs. " + nf.format(value);
    }

    /* =========================
       MONTHLY REPORT PDF
    ========================= */

    public byte[] generatePdf(List<ItemReportDto> reports){

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // ✅ TITLE
            document.add(
                    new Paragraph("Balaji Store Summary Report")
                            .setBold()
                            .setFontSize(16)
            );

            document.add(new Paragraph(" "));

            // ✅ 7 columns (updated)
            Table table = new Table(7);

            table.addCell("Item");
            table.addCell("Purchased");
            table.addCell("Used");
            table.addCell("Purchase ₹");
            table.addCell("Usage ₹");
            table.addCell("Closing Stock");
            table.addCell("Stock Value ₹");

            double totalPurchased = 0;
            double totalUsed = 0;
            double totalPurchaseAmt = 0;
            double totalUsageAmt = 0;
            double totalClosing = 0;
            double totalStockValue = 0;

            for(ItemReportDto r : reports){

                totalPurchased += r.getPurchased();
                totalUsed += r.getUsed();
                totalPurchaseAmt += r.getPurchaseAmount();
                totalUsageAmt += r.getUsageAmount();
                totalClosing += r.getClosingStock();
                totalStockValue += r.getStockValue();

                table.addCell(r.getItemName());

                table.addCell(String.format("%.2f", r.getPurchased()));
                table.addCell(String.format("%.2f", r.getUsed()));

                table.addCell(formatRs(r.getPurchaseAmount()));
                table.addCell(formatRs(r.getUsageAmount()));

                table.addCell(String.format("%.2f", r.getClosingStock()));
                table.addCell(formatRs(r.getStockValue()));
            }

            // ✅ TOTAL ROW
            table.addCell(new Paragraph("TOTAL").setBold());
            table.addCell(String.format("%.2f", totalPurchased));
            table.addCell(String.format("%.2f", totalUsed));
            table.addCell(formatRs(totalPurchaseAmt));
            table.addCell(formatRs(totalUsageAmt));
            table.addCell(String.format("%.2f", totalClosing));
            table.addCell(formatRs(totalStockValue));

            document.add(table);
            document.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    /* =========================
       ITEM DAYWISE PDF
    ========================= */

    public byte[] generateItemDaywisePdf(
            List<ItemReportDto> reports
    ){

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        try{

            PdfWriter writer =
                    new PdfWriter(out);

            PdfDocument pdf =
                    new PdfDocument(writer);

            Document document =
                    new Document(pdf);

            String itemName =

                    reports!=null &&
                            !reports.isEmpty()

                            ?

                            reports.get(0)
                                    .getItemName()

                            :

                            "Unknown Item";

            document.add(

                    new Paragraph(

                            "Item Daywise Report of "
                                    + itemName

                    )

                            .setBold()

                            .setFontSize(16)

            );

            document.add(
                    new Paragraph(" ")
            );

            // 9 columns same as screen
            Table table =
                    new Table(9);

            table.addCell("Date");
            table.addCell("Item");
            table.addCell("Opening");
            table.addCell("Purchased");
            table.addCell("Used");
            table.addCell("Closing");
            table.addCell("Purchase ₹");
            table.addCell("Usage ₹");
            table.addCell("Stock Value ₹");

            double totalOpening = 0;
            double totalPurchased = 0;
            double totalUsed = 0;
            double totalClosing = 0;
            double totalPurchaseAmt = 0;
            double totalUsageAmt = 0;
            double totalStockValue = 0;

            boolean firstRow = true;

            for(ItemReportDto r : reports){

                if(firstRow){

                    totalOpening =
                            r.getOpeningStock();

                    firstRow = false;
                }

                totalPurchased +=
                        r.getPurchased();

                totalUsed +=
                        r.getUsed();

                totalClosing =
                        r.getClosingStock();

                totalPurchaseAmt +=
                        r.getPurchaseAmount();

                totalUsageAmt +=
                        r.getUsageAmount();

                totalStockValue +=
                        r.getStockValue();

                table.addCell(
                        String.valueOf(
                                r.getDate()
                        )
                );

                table.addCell(
                        r.getItemName()
                );

                table.addCell(
                        String.format(
                                "%.2f",
                                r.getOpeningStock()
                        )
                );

                table.addCell(
                        String.format(
                                "%.2f",
                                r.getPurchased()
                        )
                );

                table.addCell(
                        String.format(
                                "%.2f",
                                r.getUsed()
                        )
                );

                table.addCell(
                        String.format(
                                "%.2f",
                                r.getClosingStock()
                        )
                );

                table.addCell(
                        formatRs(
                                r.getPurchaseAmount()
                        )
                );

                table.addCell(
                        formatRs(
                                r.getUsageAmount()
                        )
                );

                table.addCell(
                        formatRs(
                                r.getStockValue()
                        )
                );
            }

            // Closing Balance Row
            table.addCell(
                    new Paragraph("Closing Balance")
                            .setBold()
            );

            table.addCell("-");

            table.addCell(
                    String.format(
                            "%.2f",
                            totalOpening
                    )
            );

            table.addCell(
                    String.format(
                            "%.2f",
                            totalPurchased
                    )
            );

            table.addCell(
                    String.format(
                            "%.2f",
                            totalUsed
                    )
            );

            table.addCell(
                    String.format(
                            "%.2f",
                            totalClosing
                    )
            );

            table.addCell(
                    formatRs(
                            totalPurchaseAmt
                    )
            );

            table.addCell(
                    formatRs(
                            totalUsageAmt
                    )
            );

            table.addCell(
                    formatRs(
                            totalStockValue
                    )
            );

            document.add(table);

            document.close();

        }

        catch(Exception e){

            e.printStackTrace();

        }

        return out.toByteArray();

    }

}