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

            double updatedQty = currentQty + entry.getQuantity();

            product.setQuantity(updatedQty);

            // keep latest purchase price as reference
            product.setPrice(entry.getPrice());

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

        productRepository.save(product);

        return entryRepository.save(entry);
    }

    // =========================
    // UPDATE ENTRY
    // =========================
    public DailyEntry updateEntry(Long id, DailyEntry newEntry) {

        DailyEntry oldEntry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        Product product = productRepository.findById(newEntry.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double qty = product.getQuantity();

        // REMOVE OLD EFFECT
        if (oldEntry.getType().equalsIgnoreCase("purchase")) {
            qty -= oldEntry.getQuantity();
        } else {
            qty += oldEntry.getQuantity();
        }

        // APPLY NEW EFFECT
        if (newEntry.getType().equalsIgnoreCase("purchase")) {
            qty += newEntry.getQuantity();
        } else {
            qty -= newEntry.getQuantity();
        }

        if (qty < 0) {
            throw new RuntimeException("Insufficient stock after update");
        }

        product.setQuantity(qty);

        productRepository.save(product);

        oldEntry.setProductId(product.getId());
        oldEntry.setType(newEntry.getType());
        oldEntry.setQuantity(newEntry.getQuantity());
        oldEntry.setPrice(newEntry.getPrice());
        oldEntry.setEntryTime(newEntry.getEntryTime());

        return entryRepository.save(oldEntry);
    }

    // =========================
    // DELETE ENTRY (SOFT DELETE)
    // =========================
    public void deleteEntry(Long id) {

        DailyEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        Product product = productRepository.findById(entry.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double qty = product.getQuantity();

        if (entry.getType().equalsIgnoreCase("purchase")) {
            qty -= entry.getQuantity();
        } else {
            qty += entry.getQuantity();
        }

        product.setQuantity(qty);

        entry.setDeleted(true);

        productRepository.save(product);
        entryRepository.save(entry);
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
    }

    // =========================
    // PERMANENT DELETE
    // =========================
    public void deletePermanently(Long id) {
        entryRepository.deleteById(id);
    }
}