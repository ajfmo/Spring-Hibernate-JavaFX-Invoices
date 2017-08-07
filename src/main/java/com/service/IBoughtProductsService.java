package com.service;

import com.entity.BoughtProducts;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IBoughtProductsService
{
    List<BoughtProducts> findAll();
    BoughtProducts save(BoughtProducts product);
    void delete(BoughtProducts product);
    int update(BigDecimal quantity, BigDecimal netValue, BigDecimal taxValue, BigDecimal grossValue, Date sellDate, int id);
}
