package org.BalajiStore.Repository;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Dto.ReportDto;
import org.BalajiStore.Model.DailyEntry;
import org.BalajiStore.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {

    // ✅ 1. DATE RANGE REPORT (for Reports page)
    @Query("""
SELECT new org.BalajiStore.Dto.ItemReportDto(

    p.name,

    CASE
        WHEN (
            COALESCE(p.quantity, 0.0)
            - COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0.0 END), 0.0)
            + COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0.0 END), 0.0)
        ) < 0
        THEN 0.0
        ELSE (
            COALESCE(p.quantity, 0.0)
            - COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0.0 END), 0.0)
            + COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0.0 END), 0.0)
        )
    END,

    COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0.0 END), 0.0),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0.0 END), 0.0),

    COALESCE(p.quantity, 0.0),

    COALESCE(SUM(CASE 
        WHEN LOWER(e.type)='purchase' 
        THEN e.quantity * COALESCE(e.price,0.0) 
        ELSE 0.0 END), 0.0),

    COALESCE(SUM(CASE 
        WHEN LOWER(e.type)='usage' 
        THEN e.quantity * COALESCE(e.price,0.0) 
        ELSE 0.0 END), 0.0),

    (COALESCE(p.quantity,0.0) * COALESCE(p.price,0.0)),

    NULL   
)

FROM Product p
LEFT JOIN DailyEntry e
ON p.id = e.productId

WHERE e.entryTime BETWEEN :start AND :end

GROUP BY
    p.name,
    p.quantity,
    p.price

ORDER BY p.name ASC
""")
    List<ItemReportDto> getItemReport(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
SELECT new org.BalajiStore.Dto.ItemReportDto(

    p.name,

    CASE
        WHEN (
            COALESCE(p.quantity, 0.0)
            - COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0.0 END), 0.0)
            + COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0.0 END), 0.0)
        ) < 0 THEN 0.0
        ELSE (
            COALESCE(p.quantity, 0.0)
            - COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0.0 END), 0.0)
            + COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0.0 END), 0.0)
        )
    END,

    COALESCE(
        SUM(
            CASE
                WHEN LOWER(e.type)='purchase'
                THEN e.quantity
                ELSE 0.0
            END
        ),
        0.0
    ),

    COALESCE(
        SUM(
            CASE
                WHEN LOWER(e.type)='usage'
                THEN e.quantity
                ELSE 0.0
            END
        ),
        0.0
    ),

    COALESCE(p.quantity, 0.0),

    COALESCE(
        SUM(
            CASE
                WHEN LOWER(e.type)='purchase'
                THEN e.quantity * COALESCE(e.price, 0.0)
                ELSE 0.0
            END
        ),
        0.0
    ),

    COALESCE(
        SUM(
            CASE
                WHEN LOWER(e.type)='usage'
                THEN e.quantity * COALESCE(e.price, 0.0)
                ELSE 0.0
            END
        ),
        0.0
    ),

    (COALESCE(p.quantity, 0.0) * COALESCE(p.price, 0.0)),

    COALESCE(e.entryTime, p.createdDate)

)

FROM Product p

LEFT JOIN DailyEntry e
ON p.id = e.productId

WHERE LOWER(TRIM(p.name))
=
LOWER(TRIM(:name))

GROUP BY
    p.name,
    p.quantity,
    p.price,
    e.entryTime,
    p.createdDate

ORDER BY COALESCE(e.entryTime, p.createdDate) DESC
""")
    List<ItemReportDto> getItemDaywiseReport(@Param("name") String name);

    @Query("""
SELECT new org.BalajiStore.Dto.ReportDto(

    COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0 END), 0),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0 END), 0),

    COALESCE(SUM(CASE WHEN LOWER(e.type)='purchase' THEN e.quantity ELSE 0 END), 0)
    -
    COALESCE(SUM(CASE WHEN LOWER(e.type)='usage' THEN e.quantity ELSE 0 END), 0)

)
FROM DailyEntry e
WHERE e.entryTime BETWEEN :start AND :end
""")
    ReportDto getSummary(@Param("start") LocalDate start,
                         @Param("end") LocalDate end);
    // ✅ optional (keep)
    List<DailyEntry> findByEntryTime(LocalDate date);
    List<DailyEntry> findByEntryTimeAndDeletedFalse(LocalDate date);

    List<DailyEntry> findByDeletedTrue();
}