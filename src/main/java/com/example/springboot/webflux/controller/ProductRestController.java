package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.models.dao.ProductRepository;
import com.example.springboot.webflux.models.documents.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/products")
public class ProductRestController {

  private final ProductRepository productRepository;
  
  @GetMapping
  public Flux<Product> index() {

    return productRepository.findAll()
        .map(product -> {
          product.setName(product.getName().toUpperCase());
          return product;
        })
        .doOnNext(product -> log.info(product.getName()));
  }
  
  @GetMapping("/{id}")
  public Mono<Product> show(@PathVariable String id) {
    
    //final var product = productRepository.findById(id);
    
    final var products = productRepository.findAll();

    return products.filter(product1 -> product1.getId().equalsIgnoreCase(id))
        .next()// With next(), we can obtain Mono from Flux automatically
        .doOnNext(product1 -> log.info(product1.getName()));
  }
}
