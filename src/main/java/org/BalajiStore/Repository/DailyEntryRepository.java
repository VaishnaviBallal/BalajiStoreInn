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

    (p.quantity
      - COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' 
      AND e.entryTime BETWEEN :start AND :end 
      THEN e.quantity ELSE 0 END),0)
      + COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' 
      AND e.entryTime BETWEEN :start AND :end 
      THEN e.quantity ELSE 0 END),0)
    ),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' 
      AND e.entryTime BETWEEN :start AND :end 
      THEN e.quantity ELSE 0 END),0),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' 
      AND e.entryTime BETWEEN :start AND :end 
      THEN e.quantity ELSE 0 END),0),

    p.quantity
)

FROM Product p
LEFT JOIN DailyEntry e
ON LOWER(p.name) = LOWER(e.itemName)

GROUP BY p.name, p.quantity
""")
    List<ItemReportDto> getItemReport(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    // Today entries for Daily Entry page
    List<DailyEntry> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);
}