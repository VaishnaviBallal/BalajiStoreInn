package org.BalajiStore.Service;

import org.BalajiStore.Entity.Product;
import org.BalajiStore.Model.DailyEntry;
import org.BalajiStore.Repository.DailyEntryRepository;
import org.BalajiStore.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyEntryService {

    @Autowired
    private DailyEntryRepository entryRepository;

    @Autowired
    private ProductRepository productRepository;

    // =========================
    // SAVE ENTRY
    // =========================
    public DailyEntry saveEntry(DailyEntry entry) {

        if (entry.getEntryTime() == null) {
            entry.setEntryTime(LocalDate.now());
        }

        Product product = productRepository.findById(entry.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + entry.getProductId())
                );

        double currentQty =
                product.getQuantity() == null ? 0.0 : product.getQuantity();

        // =========================
        // PURCHASE
        // =========================
        if (entry.getType().equalsIgnoreCase("purchase")) {

            double purchaseQty = entry.getQuantity();

            double purchasePrice = entry.getPrice();

            double currentPrice =
                    product.getPrice() == null
                            ? product.getOpeningPrice()
                            : product.getPrice();

            double currentValue =
                    currentQty * currentPrice;

            double purchaseValue =
                    purchaseQty * purchasePrice;

            double newQty =
                    currentQty + purchaseQty;

            double newAveragePrice =
                    newQty == 0
                            ? 0
                            : (currentValue + purchaseValue) / newQty;
            product.setQuantity(newQty);

            product.setPrice(newAveragePrice);

        }

        // =========================
        // USAGE
        // =========================
        else if (entry.getType().equalsIgnoreCase("usage")) {

            if (currentQty < entry.getQuantity()) {
                throw new RuntimeException("Not enough stock available");
            }

            double updatedQty = currentQty - entry.getQuantity();

            product.setQuantity(updatedQty);

            // usage uses product price (snapshot not needed here)
            entry.setPrice(product.getPrice());
        }

        DailyEntry saved = entryRepository.save(entry);

        recalculateProduct(product.getId());

        return saved;
    }

    // =========================
    // UPDATE ENTRY
    // =========================
    public DailyEntry updateEntry(Long id, DailyEntry newEntry) {

        DailyEntry oldEntry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        Product product = productRepository.findById(newEntry.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));



        oldEntry.setProductId(product.getId());
        oldEntry.setType(newEntry.getType());
        oldEntry.setQuantity(newEntry.getQuantity());
        oldEntry.setPrice(newEntry.getPrice());
        oldEntry.setEntryTime(newEntry.getEntryTime());

        DailyEntry saved = entryRepository.save(oldEntry);

        recalculateProduct(product.getId());

        return saved;
    }

    // =========================
    // DELETE ENTRY (SOFT DELETE)
    // =========================
    public void deleteEntry(Long id) {

        DailyEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        Product product = productRepository.findById(entry.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        entry.setDeleted(true);

        entryRepository.save(entry);

        recalculateProduct(product.getId());
    }

    // =========================
    // GET TODAY ENTRIES
    // =========================
    public List<DailyEntry> getAllEntries() {
        return entryRepository.findByEntryTimeAndDeletedFalse(LocalDate.now());
    }

    // =========================
    // GET BY DATE
    // =========================
    public List<DailyEntry> getEntriesByDate(String date) {
        return entryRepository.findByEntryTimeAndDeletedFalse(LocalDate.parse(date));
    }

    // =========================
    // GET ALL
    // =========================
    public List<DailyEntry> getAll() {
        return entryRepository.findByDeletedFalse();
    }

    // =========================
    // BIN
    // =========================
    public List<DailyEntry> getBinEntries() {
        return entryRepository.findByDeletedTrue();
    }

    // =========================
    // RESTORE
    // =========================
    public void restoreEntry(Long id) {
        DailyEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        entry.setDeleted(false);

        entryRepository.save(entry);

        recalculateProduct(entry.getProductId());
    }

    // =========================
    // PERMANENT DELETE
    // =========================
    public void deletePermanently(Long id) {

        DailyEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        Long productId = entry.getProductId();

        entryRepository.delete(entry);

        recalculateProduct(productId);
    }

    private void recalculateProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double qty = product.getOpeningQuantity() == null
                ? 0
                : product.getOpeningQuantity();

        double avgPrice = product.getOpeningPrice() == null
                ? 0
                : product.getOpeningPrice();

        List<DailyEntry> entries =
                entryRepository.findByProductIdAndDeletedFalseOrderByEntryTimeAscIdAsc(productId);

        for (DailyEntry entry : entries) {

            if (entry.getType().equalsIgnoreCase("purchase")) {

                double purchaseQty = entry.getQuantity();
                double purchasePrice = entry.getPrice();

                double currentValue = qty * avgPrice;
                double purchaseValue = purchaseQty * purchasePrice;

                qty += purchaseQty;

                avgPrice = qty == 0
                        ? 0
                        : (currentValue + purchaseValue) / qty;

            } else if (entry.getType().equalsIgnoreCase("usage")) {

                qty -= entry.getQuantity();

                if (qty < 0) {
                    qty = 0;
                }
            }
        }

        product.setQuantity(qty);
        product.setPrice(avgPrice);

        productRepository.save(product);
    }
}