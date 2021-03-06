package com.serviceImples;

import com.entity.BoughtProduct;
import com.repositories.IBoughtProductsRepository;
import com.service.IBoughtProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoughtProductsServImp implements IBoughtProductsService {
    @Autowired
    private IBoughtProductsRepository boughtProductsRepository;

    @Override
    public List<BoughtProduct> findAll() {
        return boughtProductsRepository.findAll();
    }

    @Override
    @Transactional
    public BoughtProduct save(BoughtProduct product) {
        return boughtProductsRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(BoughtProduct product) {
        boughtProductsRepository.delete(product);
    }
}
