package org.BalajiStore.Service;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Repository.DailyEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final DailyEntryRepository repository;

    public ReportService(
            DailyEntryRepository repository
    ) {
        this.repository = repository;
    }

    // =========================
    // DATE RANGE REPORT
    // =========================

    public List<ItemReportDto> getReport(
            String start,
            String end
    ) {

        LocalDate startDate =
                LocalDate.parse(start);

        LocalDate endDate =
                LocalDate.parse(end);

        return repository.getItemReport(
                startDate,
                endDate
        );
    }

    // =========================
    // ITEM SUMMARY
    // =========================

    public ItemReportDto getItemByName(
            String name
    ) {

        List<ItemReportDto> list =
                getItemDaywiseReport(
                        name
                );

        if(
                list == null ||
                        list.isEmpty()
        ){

            return new ItemReportDto(

                    name,

                    0.0,

                    0.0,

                    0.0,

                    0.0,

                    0.0,

                    0.0,

                    0.0,

                    null
            );
        }

        // latest row

        return list.get(
                list.size()-1
        );
    }

    // =========================
    // DAYWISE REPORT
    // =========================

    public List<ItemReportDto>
    getItemDaywiseReport(
            String name
    ) {

        List<ItemReportDto> list =

                repository
                        .getItemDaywiseReport(
                                name.trim()
                        );

        double runningStock = 0;

        double lastPrice = 0;

        for(
                ItemReportDto r
                :
                list
        ){

            double purchased =

                    r.getPurchased()==null

                            ?

                            0

                            :

                            r.getPurchased();

            double used =

                    r.getUsed()==null

                            ?

                            0

                            :

                            r.getUsed();

            double purchaseAmount =

                    r.getPurchaseAmount()==null

                            ?

                            0

                            :

                            r.getPurchaseAmount();

            // Opening Stock

            r.setOpeningStock(
                    runningStock
            );

            // update latest price only when purchase exists

            if(
                    purchased > 0
            ){

                lastPrice =

                        purchaseAmount

                                /

                                purchased;
            }

            // =========================
            // PREVENT NEGATIVE STOCK
            // =========================

            if (
                    used >
                            runningStock + purchased
            ) {

                throw new RuntimeException(

                        "Insufficient Stock. Available: "

                                +

                                (
                                        runningStock
                                                +
                                                purchased
                                )

                );
            }

            // running stock

            runningStock =

                    runningStock

                            +

                            purchased

                            -

                            used;

            // closing stock

            r.setClosingStock(
                    runningStock
            );

            // stock value

            r.setStockValue(

                    runningStock

                            *

                            lastPrice

            );

        }

        return list;
    }

}