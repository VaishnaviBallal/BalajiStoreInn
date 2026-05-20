package org.BalajiStore.Controller;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ReportService reportService;

    @GetMapping
    public ItemReportDto getItem(@RequestParam String name) {
        return reportService.getItemByName(name.trim());
    }

    @GetMapping("/daywise")
    public List<ItemReportDto> getDaywise(@RequestParam String name) {
        return reportService.getItemDaywiseReport(name);
    }
}
