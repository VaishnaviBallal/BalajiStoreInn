package org.BalajiStore.Service;

import org.BalajiStore.Entity.Product;
import org.BalajiStore.Model.DailyEntry;
import org.BalajiStore.Repository.DailyEntryRepository;
import org.BalajiStore.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyEntryService {

    @Autowired
    private DailyEntryRepository entryRepository;

    @Autowired
    private ProductRepository productRepository;

    public DailyEntry saveEntry(DailyEntry entry) {

        System.out.println("Item Name : " + entry.getItemName());
        System.out.println("Type : " + entry.getType());
        System.out.println("Quantity : " + entry.getQuantity());

        // set entry time automatically
        entry.setEntryTime(LocalDateTime.now());

        // find product
        Product product = productRepository.findByNameIgnoreCase(entry.getItemName());

        if (product == null) {
            throw new RuntimeException("Product not found: " + entry.getItemName());
        }

        int currentQty = product.getQuantity();

        // purchase case
        if (entry.getType().equalsIgnoreCase("purchase")) {

            int updatedQty = currentQty + entry.getQuantity();
            product.setQuantity(updatedQty);

        }

        // usage case
        else if (entry.getType().equalsIgnoreCase("usage")) {

            if (currentQty < entry.getQuantity()) {
                throw new RuntimeException("Not enough stock available");
            }

            int updatedQty = currentQty - entry.getQuantity();
            product.setQuantity(updatedQty);
        }

        // save updated product stock
        productRepository.save(product);

        // save daily entry
        return entryRepository.save(entry);
    }

    public List<DailyEntry> getAllEntries() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        return entryRepository.findByEntryTimeBetween(start, end);
    }
    public void deleteEntry(Long id) {

        if(entryRepository.existsById(id)) {

            entryRepository.deleteById(id);

        } else {

            throw new RuntimeException("Entry not found");

        }
    }
}