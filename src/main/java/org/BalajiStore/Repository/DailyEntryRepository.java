package org.BalajiStore.Repository;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Model.DailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {

    @Query("""
SELECT new org.BalajiStore.Dto.ItemReportDto(

    p.name,

    (p.quantity
      - COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0 END),0)
      + COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0 END),0)
    ),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0 END),0),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0 END),0),

    p.quantity,

    COALESCE(
      CASE 
        WHEN COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0 END),0) = 0
        THEN 0
        ELSE (
          COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase'
              THEN e.quantity * COALESCE(e.price,0) ELSE 0 END),0)
          /
          COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase'
              THEN e.quantity ELSE 0 END),0)
        ) * p.quantity
      END
    , 0)

)
FROM Product p
LEFT JOIN DailyEntry e
ON LOWER(TRIM(p.name)) = LOWER(TRIM(e.itemName))
AND e.entryTime BETWEEN :start AND :end   
GROUP BY p.name, p.quantity, p.price
""")
    List<ItemReportDto> getItemReport(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    List<DailyEntry> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);
}