package org.BalajiStore.Service;

import org.BalajiStore.Entity.Product;
import org.BalajiStore.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // =========================
    // SAVE PRODUCT
    // =========================
    public Product saveProduct(Product product) {

        if (product.getCreatedDate() == null) {
            product.setCreatedDate(LocalDate.now());
        }

        // ONLY set opening stock ON FIRST CREATE
        if (product.getId() == null) {

            product.setOpeningQuantity(product.getQuantity());

            product.setOpeningPrice(product.getPrice());
        }

        return productRepository.save(product);
    }

    // =========================
    // GET ALL
    // =========================
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // =========================
    // GET BY ID
    // =========================
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // =========================
    // UPDATE PRODUCT (SAFE)
    // =========================
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productRepository.findById(id)
                .orElse(null);

        if (existingProduct != null) {

            existingProduct.setName(product.getName());
            existingProduct.setUnit(product.getUnit());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setOpeningQuantity(product.getOpeningQuantity());
            existingProduct.setOpeningPrice(product.getOpeningPrice());

            // ⚠️ IMPORTANT:
            // DO NOT overwrite quantity manually here
            // because stock is controlled by DailyEntry

            return productRepository.save(existingProduct);
        }

        return null;
    }

    // =========================
    // LOW STOCK
    // =========================
    public List<Product> getLowStockProducts() {
        return productRepository.findByQuantityLessThan(10);
    }

    // =========================
    // DELETE
    // =========================
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}