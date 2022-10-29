package com.example.springboot.webflux.service;

import com.example.springboot.webflux.models.documents.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
  
  Flux<Product> findAll();

  Flux<Product> findAllNamesUppercase();

  Flux<Product> findAllNamesUppercaseWithRepeat();
  Mono<Product> findById(String id);
  
  Mono<Product> save(Product product);

  Mono<Void> delete(Product product);

}
