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



    public DailyEntry saveEntry(DailyEntry entry) {

        if (entry.getEntryTime() == null) {
            entry.setEntryTime(LocalDate.now());
        }

        Product product = productRepository.findByNameIgnoreCase(entry.getItemName());

        if (product == null) {
            throw new RuntimeException("Product not found: " + entry.getItemName());
        }

        Double currentQty = product.getQuantity();

        if (entry.getType().equalsIgnoreCase("purchase")) {

            product.setQuantity(currentQty + entry.getQuantity());
            product.setPrice(entry.getPrice());

        } else if (entry.getType().equalsIgnoreCase("usage")) {

            if (currentQty < entry.getQuantity()) {
                throw new RuntimeException("Not enough stock available");
            }

            product.setQuantity(currentQty - entry.getQuantity());

            // ✅ ensure usage uses product price
            entry.setPrice(product.getPrice());
        }

        // ✅ IMPORTANT FIX (force calculation)
        if (entry.getQuantity() != null && entry.getPrice() != null) {
            entry.setTotalPrice(entry.getQuantity() * entry.getPrice());
        } else {
            entry.setTotalPrice(0.0);
        }

        productRepository.save(product);
        System.out.println("QTY: " + entry.getQuantity());
        System.out.println("PRICE: " + entry.getPrice());
        System.out.println("TOTAL BEFORE SAVE: " + entry.getTotalPrice());

        return entryRepository.save(entry);
    }

    public List<DailyEntry> getAllEntries() {
        return entryRepository.findByEntryTime(LocalDate.now());
    }

    public void deleteEntry(Long id) {

        if (entryRepository.existsById(id)) {
            entryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Entry not found");
        }
    }

    public List<DailyEntry> getEntriesByDate(String date) {
        LocalDate selectedDate = LocalDate.parse(date);
        return entryRepository.findByEntryTime(selectedDate);
    }

    public List<DailyEntry> getAll() {
        return entryRepository.findAll();
    }

    public DailyEntry updateEntry(Long id, DailyEntry entry) {

        DailyEntry existing = entryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        existing.setItemName(entry.getItemName());
        existing.setType(entry.getType());
        existing.setQuantity(entry.getQuantity());
        existing.setPrice(entry.getPrice());

        if (entry.getEntryTime() != null) {
            existing.setEntryTime(entry.getEntryTime());
        }

        // ✅ recalculate on update
        if (existing.getQuantity() != null && existing.getPrice() != null) {
            existing.setTotalPrice(existing.getQuantity() * existing.getPrice());
        }

        return entryRepository.save(existing);
    }
}