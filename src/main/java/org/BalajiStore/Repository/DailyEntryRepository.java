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

    e.itemName,
    e.quantity,
    e.type,
    e.price,
    e.entryTime

)

FROM DailyEntry e

WHERE e.entryTime BETWEEN :start AND :end

ORDER BY e.entryTime DESC
""")
    List<ItemReportDto> getItemReport(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    List<DailyEntry> findByEntryTime(LocalDate date);
}