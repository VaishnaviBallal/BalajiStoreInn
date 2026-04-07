package org.BalajiStore.Service;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Repository.DailyEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final DailyEntryRepository repository;

    public ReportService(DailyEntryRepository repository) {
        this.repository = repository;
    }

    public List<ItemReportDto> getReport(String start, String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        List<ItemReportDto> reportList =
                repository.getItemReport(startDate, endDate);

        // ✅ ADD THIS (date range)
        String dateRange = start + " to " + end;

        for (ItemReportDto item : reportList) {
            item.setDateRange(dateRange);
        }

        return reportList;
    }
}