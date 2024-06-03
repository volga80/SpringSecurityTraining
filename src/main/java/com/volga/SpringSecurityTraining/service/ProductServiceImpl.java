package com.volga.SpringSecurityTraining.service;

import com.volga.SpringSecurityTraining.dao.ProductRepository;
import com.volga.SpringSecurityTraining.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
