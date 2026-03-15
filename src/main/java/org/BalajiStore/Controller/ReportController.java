package org.BalajiStore.Controller;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin
public class ReportController {

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
}