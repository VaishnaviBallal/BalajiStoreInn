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

        return repository.getItemReport(startDate, endDate);
    }
    public ItemReportDto getItemByName(String name) {

        List<ItemReportDto> list = repository.getItemByName(name);

        if (list.isEmpty()) {
            throw new RuntimeException("Item not found");
        }

        return list.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

    }
}