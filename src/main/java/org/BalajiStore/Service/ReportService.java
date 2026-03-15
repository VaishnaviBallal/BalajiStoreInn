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

        LocalDateTime startDate =
                LocalDate.parse(start).atStartOfDay();

        LocalDateTime endDate =
                LocalDate.parse(end).atTime(23,59,59);

        return repository.getItemReport(startDate, endDate);
    }
}