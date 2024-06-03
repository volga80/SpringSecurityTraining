package com.volga.SpringSecurityTraining.dao;

import com.volga.SpringSecurityTraining.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
