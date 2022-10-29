package com.example.springboot.webflux.models.dao;

import com.example.springboot.webflux.models.documents.Product;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
