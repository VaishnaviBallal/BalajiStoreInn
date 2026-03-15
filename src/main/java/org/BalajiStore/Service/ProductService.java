package org.BalajiStore.Service;

import org.BalajiStore.Entity.Product;
import org.BalajiStore.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();

    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setPrice(product.getPrice());

            return productRepository.save(existingProduct);
        }

        return null;
    }
    public List<Product> getLowStockProducts() {
        return productRepository.findByQuantityLessThan(10);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}