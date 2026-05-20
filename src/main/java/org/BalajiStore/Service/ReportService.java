package org.BalajiStore.Service;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Repository.DailyEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final DailyEntryRepository repository;

    public ReportService(DailyEntryRepository repository) {
        this.repository = repository;
    }

    // =========================
    // DATE RANGE REPORT
    // =========================
    public List<ItemReportDto> getReport(String start, String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return repository.getItemReport(startDate, endDate);
    }

    // =========================
    // ITEM SUMMARY (FIXED SAFE)
    // =========================
    public ItemReportDto getItemByName(String name) {

        String cleanName = name.trim();

        List<ItemReportDto> list =
                repository.getItemDaywiseReport(cleanName);

        if (list == null || list.isEmpty()) {
            return new ItemReportDto(
                    cleanName,
                    0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0,
                    null
            );
        }

        // return latest record (safe fallback)
        return list.get(0);
    }

    // =========================
    // DAYWISE REPORT
    // =========================
    public List<ItemReportDto> getItemDaywiseReport(String name) {
        return repository.getItemDaywiseReport(name.trim());
    }
}