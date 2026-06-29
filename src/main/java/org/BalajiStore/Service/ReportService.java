package org.BalajiStore.Service;

import org.BalajiStore.Dto.ItemReportDto;
import org.BalajiStore.Entity.Product;
import org.BalajiStore.Repository.DailyEntryRepository;
import org.BalajiStore.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;



@Service
public class ReportService {
    @Autowired
    private ProductRepository productRepository;

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

        List<ItemReportDto> list =
                repository.getItemReport(startDate, endDate);

        for (ItemReportDto r : list) {

            Product product =
                    productRepository.findByNameIgnoreCase(r.getItemName());

            if (product != null) {

                r.setStockValue(
                        r.getClosingStock() * product.getPrice()
                );
            }
        }

        return list;
    }

    // =========================
    // ITEM SUMMARY
    // =========================

    public ItemReportDto getItemSummary(
            String name,
            String start,
            String end
    ) {

        List<ItemReportDto> list =
                getItemDaywiseReport(name, start, end);

        double totalPurchased = 0;
        double totalUsed = 0;

        for (ItemReportDto r : list) {

            totalPurchased +=
                    r.getPurchased() == null
                            ? 0
                            : r.getPurchased();

            totalUsed +=
                    r.getUsed() == null
                            ? 0
                            : r.getUsed();
        }

        Product product =
                productRepository.findByNameIgnoreCase(name);

        double opening = 0;

        if (product != null) {

            opening =
                    product.getOpeningQuantity() == null
                            ? 0
                            : product.getOpeningQuantity();

            LocalDate startDate =
                    LocalDate.parse(start);

            Double movement =
                    repository.getStockMovementBeforeDate(
                            product.getId(),
                            startDate
                    );

            opening +=
                    movement == null
                            ? 0
                            : movement;
        }

        double closing =
                opening
                        + totalPurchased
                        - totalUsed;

        ItemReportDto dto = new ItemReportDto();

        dto.setItemName(name);
        dto.setOpeningStock(opening);
        dto.setPurchased(totalPurchased);
        dto.setUsed(totalUsed);
        dto.setClosingStock(closing);

        return dto;
    }

    // =========================
    // DAYWISE REPORT
    // =========================

    public List<ItemReportDto> getItemDaywiseReport(
            String name,
            String start,
            String end
    ) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        List<ItemReportDto> list =
                repository.getItemDaywiseReport(
                        name.trim(),
                        startDate,
                        endDate
                );

        Product product =
                productRepository.findByNameIgnoreCase(name);

        double openingStock = 0;

        if (product != null) {

            openingStock =
                    product.getOpeningQuantity() == null
                            ? 0
                            : product.getOpeningQuantity();

            Double movement =
                    repository.getStockMovementBeforeDate(
                            product.getId(),
                            startDate
                    );

            openingStock +=
                    movement == null
                            ? 0
                            : movement;
        }

        double runningStock = openingStock;


        for (ItemReportDto r : list) {

            double purchased =
                    r.getPurchased() == null
                            ? 0
                            : r.getPurchased();

            double used =
                    r.getUsed() == null
                            ? 0
                            : r.getUsed();

            r.setOpeningStock(runningStock);

            runningStock =
                    runningStock
                            + purchased
                            - used;

            r.setClosingStock(runningStock);



            r.setStockValue(
                    runningStock * product.getPrice()
            );
        }

        return list;
    }
    public List<ItemReportDto> getSummaryReport(String start, String end) {

        List<ItemReportDto> list = repository.getItemSummaryReport(
                LocalDate.parse(start),
                LocalDate.parse(end)
        );

        // ✅ ADD THIS BLOCK HERE
        for (ItemReportDto r : list) {

            double closing = r.getClosingStock() == null ? 0 : r.getClosingStock();


            Product product =
                    productRepository.findByNameIgnoreCase(
                            r.getItemName());

            double price =
                    product == null
                            ? 0
                            : product.getPrice();

            r.setStockValue(
                    r.getClosingStock() * price
            );
        }

        return list;
    }
}