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
    // SAVE ENTRY (FIXED)
    // =========================
    public DailyEntry saveEntry(DailyEntry entry) {

        if (entry.getEntryTime() == null) {
            entry.setEntryTime(LocalDate.now());
        }

        // ✅ MUST USE productId (NOT NAME)
        Product product = productRepository.findById(entry.getProductId())
                .orElseThrow(() -> new RuntimeException(
                        "Product not found with id: " + entry.getProductId()
                ));

        Double currentQty = product.getQuantity();

        // store name for UI/report only
        entry.setItemName(product.getName());

        if (entry.getType().equalsIgnoreCase("purchase")) {

            product.setQuantity(currentQty + entry.getQuantity());

            // update product price on purchase
            product.setPrice(entry.getPrice());

        } else if (entry.getType().equalsIgnoreCase("usage")) {

            if (currentQty < entry.getQuantity()) {
                throw new RuntimeException("Not enough stock available");
            }

            product.setQuantity(currentQty - entry.getQuantity());

            // usage uses product price
            entry.setPrice(product.getPrice());
        }

        // calculate total
        if (entry.getQuantity() != null && entry.getPrice() != null) {
            entry.setTotalPrice(entry.getQuantity() * entry.getPrice());
        } else {
            entry.setTotalPrice(0.0);
        }

        productRepository.save(product);

        return entryRepository.save(entry);
    }

    // =========================
    // GET TODAY ENTRIES
    // =========================
    public List<DailyEntry> getAllEntries() {
        return entryRepository.findByEntryTimeAndDeletedFalse(LocalDate.now());
    }

    // =========================
    // SOFT DELETE
    // =========================
    public void deleteEntry(Long id) {

        DailyEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        entry.setDeleted(true);
        entryRepository.save(entry);
    }

    // =========================
    // GET BY DATE
    // =========================
    public List<DailyEntry> getEntriesByDate(String date) {

        LocalDate selectedDate = LocalDate.parse(date);

        return entryRepository.findByEntryTime(selectedDate);
    }

    // =========================
    // GET ALL
    // =========================
    public List<DailyEntry> getAll() {
        return entryRepository.findAll();
    }

    // =========================
    // UPDATE ENTRY (FIXED)
    // =========================
    public DailyEntry updateEntry(Long id, DailyEntry entry) {

        DailyEntry existing = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        Product product = productRepository.findById(entry.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setProductId(product.getId());
        existing.setItemName(product.getName());

        existing.setType(entry.getType());
        existing.setQuantity(entry.getQuantity());
        existing.setPrice(entry.getPrice());

        if (entry.getEntryTime() != null) {
            existing.setEntryTime(entry.getEntryTime());
        }

        if (existing.getQuantity() != null && existing.getPrice() != null) {
            existing.setTotalPrice(existing.getQuantity() * existing.getPrice());
        }

        return entryRepository.save(existing);
    }

    // =========================
    // BIN / RESTORE
    // =========================
    public List<DailyEntry> getBinEntries() {
        return entryRepository.findByDeletedTrue();
    }

    public void restoreEntry(Long id) {

        DailyEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        entry.setDeleted(false);
        entryRepository.save(entry);
    }

    public void deletePermanently(Long id) {

        if (entryRepository.existsById(id)) {
            entryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Entry not found");
        }
    }
}