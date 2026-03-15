package org.BalajiStore.Repository;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Model.DailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {

    @Query("""
SELECT new org.BalajiStore.Dto.ItemReportDto(
    p.name,
    COALESCE(SUM(CASE WHEN e.type='purchase' THEN e.quantity END),0),
    COALESCE(SUM(CASE WHEN e.type='usage' THEN e.quantity END),0),
    p.quantity
)
FROM Product p
LEFT JOIN DailyEntry e
ON p.name = e.itemName
AND e.entryTime BETWEEN :start AND :end
GROUP BY p.name, p.quantity
""")
    List<ItemReportDto> getItemReport(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    // Today entries for Daily Entry page
    List<DailyEntry> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);
}