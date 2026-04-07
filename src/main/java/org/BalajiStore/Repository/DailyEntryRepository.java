package org.BalajiStore.Repository;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Model.DailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase'
        THEN e.quantity * COALESCE(e.price,0) ELSE 0 END),0),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='usage'
        THEN e.quantity * COALESCE(e.price,0) ELSE 0 END),0),

    (p.quantity * COALESCE(p.price,0)),

    e.entryTime   -- ✅ date column
)

FROM Product p
JOIN DailyEntry e
ON LOWER(TRIM(p.name)) = LOWER(TRIM(e.itemName))

WHERE e.entryTime BETWEEN :start AND :end

GROUP BY p.name, p.quantity, p.price, e.entryTime   -- ✅ VERY IMPORTANT

ORDER BY e.entryTime DESC
""")
    List<ItemReportDto> getItemReport(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    List<DailyEntry> findByEntryTime(LocalDate date);
}