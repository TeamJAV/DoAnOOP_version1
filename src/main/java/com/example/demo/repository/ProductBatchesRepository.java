package com.example.demo.repository;

import com.example.demo.entity.ProductBatchesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductBatchesRepository extends JpaRepository<ProductBatchesEntity, String> {

    List<ProductBatchesEntity> findAllByProductsId(Integer products_id);

    @Query(value = "select * from product_batches pb where pb.sku like :sku%", nativeQuery = true)
    List<ProductBatchesEntity> findBySKU(String sku);

    @Query(value =  "SELECT pb.*\n" +
                    "FROM \n" +
                    "\tproduct_batches AS pb\n" +
                    "    INNER JOIN products AS p ON pb.products_id = p.id\n" +
                    "    INNER JOIN import_invoice AS i ON pb.import_invoice_id = i.id\n" +
                    "WHERE\n" +
                    "\tDATEDIFF(pb.expired_date, NOW()) BETWEEN 0 AND 10 AND\n" +
                    "    pb.quantity > 0\n" +
                    "ORDER BY pb.expired_date ASC, p.name ASC",
            nativeQuery = true
    )
    List<ProductBatchesEntity> getOutOfDateBatches();

    @Modifying
    @Query(value = "update product_batches pb set pb.quantity = pb.quantity - :quantity where pb.sku = :sku", nativeQuery = true)
    void updateQuantityBySku (String sku, int quantity);
}

