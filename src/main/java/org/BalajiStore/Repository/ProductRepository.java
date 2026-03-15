package org.BalajiStore.Repository;

import org.BalajiStore.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByQuantityLessThan(int quantity);
    Product findByName(String name);
    Product findByNameIgnoreCase(String name);
    @Query("SELECT COALESCE(SUM(p.quantity),0) FROM Product p")
    int getTotalStock();
}