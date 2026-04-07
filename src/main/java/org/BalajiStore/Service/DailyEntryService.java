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

        int currentQty = product.getQuantity();

        if (entry.getType().equalsIgnoreCase("purchase")) {
            product.setQuantity(currentQty + entry.getQuantity());
            product.setPrice(entry.getPrice());
        }
        else if (entry.getType().equalsIgnoreCase("usage")) {

            if (currentQty < entry.getQuantity()) {
                throw new RuntimeException("Not enough stock available");
            }

            product.setQuantity(currentQty - entry.getQuantity());
            entry.setPrice(product.getPrice());
        }

        productRepository.save(product);

        return entryRepository.save(entry);
    }

    public List<DailyEntry> getAllEntries() {
        return entryRepository.findByEntryTime(LocalDate.now());
    }
    public void deleteEntry(Long id) {

        if(entryRepository.existsById(id)) {

            entryRepository.deleteById(id);

        } else {

            throw new RuntimeException("Entry not found");

        }
    }
}