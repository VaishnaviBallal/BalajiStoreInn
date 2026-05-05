package org.BalajiStore.Controller;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Service.ReportPdfService;
import org.BalajiStore.Service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;
    private final ReportPdfService pdfService;

    // ✅ Inject BOTH properly
    public ReportController(ReportService reportService,
                            ReportPdfService pdfService) {
        this.reportService = reportService;
        this.pdfService = pdfService;
    }

    /* =========================
       DATE RANGE REPORT
    ========================= */
    @GetMapping("/items")
    public List<ItemReportDto> getReport(
            @RequestParam String start,
            @RequestParam String end) {

        return reportService.getReport(start, end);
    }

    /* =========================
       DATE RANGE PDF
    ========================= */
    @GetMapping("/items/pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @RequestParam String start,
            @RequestParam String end) {

        List<ItemReportDto> reports =
                reportService.getReport(start, end);

        byte[] pdf = pdfService.generatePdf(reports);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=store_report.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }

    /* =========================
       SINGLE ITEM SUMMARY
    ========================= */
    @GetMapping("/item")
    public ItemReportDto getItemByName(
            @RequestParam String name) {

        return reportService.getItemByName(name);
    }

    /* =========================
       DAYWISE REPORT
    ========================= */
    @GetMapping("/item/daywise")
    public List<ItemReportDto> getItemDaywiseReport(
            @RequestParam String name) {

        return reportService.getItemDaywiseReport(name);
    }

    /* =========================
       DAYWISE PDF (IMPORTANT)
    ========================= */
    @GetMapping("/item/daywise/pdf")
    public ResponseEntity<byte[]> downloadItemDaywisePdf(
            @RequestParam String name) {

        List<ItemReportDto> reports =
                reportService.getItemDaywiseReport(name);

        byte[] pdf = pdfService.generateItemDaywisePdf(reports);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=item_daywise_report.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}