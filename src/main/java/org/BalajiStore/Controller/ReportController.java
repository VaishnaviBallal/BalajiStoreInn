package org.BalajiStore.Controller;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Service.ReportPdfService;
import org.BalajiStore.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin
public class ReportController {
    @Autowired
    private ReportPdfService pdfService;
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/items")
    public List<ItemReportDto> getReport(
            @RequestParam String start,
            @RequestParam String end) {

        return reportService.getReport(start, end);
    }

    @GetMapping("/items/pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @RequestParam String start,
            @RequestParam String end) {

        List<ItemReportDto> reports = reportService.getReport(start, end);

        byte[] pdf = pdfService.generatePdf(reports);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=store_report.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
    @GetMapping("/item")
    public ItemReportDto getItemByName(@RequestParam String name) {
        return reportService.getItemByName(name);
    }
}