package org.BalajiStore.Repository;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Dto.ReportDto;
import org.BalajiStore.Model.DailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyEntryRepository
        extends JpaRepository<DailyEntry, Long> {

    // =========================
    // DATE RANGE REPORT
    // =========================

    @Query("""
SELECT new org.BalajiStore.Dto.ItemReportDto(

    p.name,

    CASE
        WHEN (
            COALESCE(p.quantity,0.0)

            -

            COALESCE(
            SUM(
            CASE
            WHEN LOWER(e.type)='purchase'
            THEN e.quantity
            ELSE 0.0
            END
            ),0.0)

            +

            COALESCE(
            SUM(
            CASE
            WHEN LOWER(e.type)='usage'
            THEN e.quantity
            ELSE 0.0
            END
            ),0.0)

        ) < 0

        THEN 0.0

        ELSE (

            COALESCE(p.quantity,0.0)

            -

            COALESCE(
            SUM(
            CASE
            WHEN LOWER(e.type)='purchase'
            THEN e.quantity
            ELSE 0.0
            END
            ),0.0)

            +

            COALESCE(
            SUM(
            CASE
            WHEN LOWER(e.type)='usage'
            THEN e.quantity
            ELSE 0.0
            END
            ),0.0)

        )

    END,

    COALESCE(
    SUM(
    CASE
    WHEN LOWER(e.type)='purchase'
    THEN e.quantity
    ELSE 0.0
    END
    ),0.0),

    COALESCE(
    SUM(
    CASE
    WHEN LOWER(e.type)='usage'
    THEN e.quantity
    ELSE 0.0
    END
    ),0.0),

    COALESCE(p.quantity,0.0),

    COALESCE(
    SUM(
    CASE
    WHEN LOWER(e.type)='purchase'
    THEN e.quantity*COALESCE(e.price,0)
    ELSE 0
    END
    ),0.0),

    COALESCE(
    SUM(
    CASE
    WHEN LOWER(e.type)='usage'
    THEN e.quantity*COALESCE(e.price,0)
    ELSE 0
    END
    ),0.0),

    COALESCE(p.quantity,0.0)
    *
    COALESCE(p.price,0.0),

    e.entryTime

)

FROM Product p

LEFT JOIN DailyEntry e
ON p.id=e.productId

WHERE

e.entryTime BETWEEN :start AND :end

AND

(
e.deleted=false
OR
e.deleted IS NULL
)

GROUP BY

p.name,
p.quantity,
p.price,
e.entryTime

ORDER BY
e.entryTime ASC,
p.name ASC

""")
    List<ItemReportDto> getItemReport(

            @Param("start")
            LocalDate start,

            @Param("end")
            LocalDate end
    );


    // =========================
    // DAYWISE REPORT
    // =========================

    @Query("""
SELECT new org.BalajiStore.Dto.ItemReportDto(

p.name,

COALESCE(p.quantity,0.0)

-

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='purchase'
THEN e.quantity
ELSE 0.0
END
),0.0)

+

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='usage'
THEN e.quantity
ELSE 0.0
END
),0.0),

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='purchase'
THEN e.quantity
ELSE 0.0
END
),0.0),

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='usage'
THEN e.quantity
ELSE 0.0
END
),0.0),

COALESCE(p.quantity,0.0),

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='purchase'
THEN e.quantity*COALESCE(e.price,0)
ELSE 0
END
),0.0),

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='usage'
THEN e.quantity*COALESCE(e.price,0)
ELSE 0
END
),0.0),

COALESCE(p.quantity,0.0)
*
COALESCE(p.price,0.0),

e.entryTime

)

FROM Product p

LEFT JOIN DailyEntry e
ON p.id=e.productId

WHERE

LOWER(TRIM(p.name))
=
LOWER(TRIM(:name))

AND

(
e.deleted=false
OR
e.deleted IS NULL
)

GROUP BY

p.name,
p.quantity,
p.price,
e.entryTime

ORDER BY
e.entryTime ASC

""")
    List<ItemReportDto> getItemDaywiseReport(

            @Param("name")
            String name

    );


    // =========================
    // SUMMARY
    // =========================

    @Query("""
SELECT new org.BalajiStore.Dto.ReportDto(

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='purchase'
THEN e.quantity
ELSE 0
END
),0),

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='usage'
THEN e.quantity
ELSE 0
END
),0),

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='purchase'
THEN e.quantity
ELSE 0
END
),0)

-

COALESCE(
SUM(
CASE
WHEN LOWER(e.type)='usage'
THEN e.quantity
ELSE 0
END
),0)

)

FROM DailyEntry e

WHERE

e.entryTime BETWEEN :start AND :end

AND

(
e.deleted=false
OR
e.deleted IS NULL
)

""")
    ReportDto getSummary(

            @Param("start")
            LocalDate start,

            @Param("end")
            LocalDate end

    );


    // =========================
    // NORMAL FETCHES
    // =========================

    List<DailyEntry>
    findByEntryTime(
            LocalDate date
    );

    List<DailyEntry>
    findByEntryTimeAndDeletedFalse(
            LocalDate date
    );

    List<DailyEntry>
    findByDeletedTrue();

    List<DailyEntry>
    findByDeletedFalse();

}