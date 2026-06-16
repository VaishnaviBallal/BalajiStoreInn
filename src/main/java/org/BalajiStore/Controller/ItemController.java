package org.BalajiStore.Controller;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ReportService reportService;

    // =========================
    // SUMMARY (single item)
    // =========================
    @GetMapping
    public ItemReportDto getItem(
            @RequestParam String name,
            @RequestParam String month
    ) {

        YearMonth ym = YearMonth.parse(month);

        return reportService.getItemSummary(
                name.trim(),
                ym.atDay(1).toString(),
                ym.atEndOfMonth().toString()
        );
    }

    // =========================
    // DAYWISE
    // =========================
    @GetMapping("/daywise")
    public List<ItemReportDto> getDaywise(
            @RequestParam String name,
            @RequestParam String month
    ) {

        YearMonth ym = YearMonth.parse(month);

        return reportService.getItemDaywiseReport(
                name.trim(),
                ym.atDay(1).toString(),
                ym.atEndOfMonth().toString()
        );
    }
}