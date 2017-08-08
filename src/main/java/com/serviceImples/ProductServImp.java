package com.serviceImples;

import com.entity.Product;
import com.repositories.IProductRepository;
import com.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class ProductServImp implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public int update(String productName, BigDecimal netPrice, BigDecimal taxRate, boolean onlineSell,
                      boolean isService, boolean isActive, int id) {
        return productRepository.update(productName, netPrice, taxRate, onlineSell, isService, isActive, id);
    }
}