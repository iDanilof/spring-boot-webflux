package com.example.springboot.webflux.service;

import com.example.springboot.webflux.models.dao.ProductRepository;
import com.example.springboot.webflux.models.documents.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
  
  private final ProductRepository productRepository;

  @Override
  public Flux<Product> findAll() {
    return productRepository.findAll();
  }

  @Override
  public Flux<Product> findAllNamesUppercase() {
    return productRepository.findAll()
        .map(product -> {
          product.setName(product.getName().toUpperCase());
          return product;
        });
  }

  @Override
  public Flux<Product> findAllNamesUppercaseWithRepeat() {
    return findAllNamesUppercase().repeat(5000);
  }

  @Override
  public Mono<Product> findById(String id) {
    return productRepository.findById(id);
  }

  @Override
  public Mono<Product> save(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Mono<Void> delete(Product product) {
    return productRepository.delete(product);
  }
}
