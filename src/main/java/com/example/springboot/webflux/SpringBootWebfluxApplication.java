package com.example.springboot.webflux;

import java.util.Date;

import com.example.springboot.webflux.models.dao.ProductRepository;
import com.example.springboot.webflux.models.documents.Product;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@SpringBootApplication
@Slf4j
public class SpringBootWebfluxApplication implements CommandLineRunner {

  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;
  
  public static void main(String[] args) {
    SpringApplication.run(SpringBootWebfluxApplication.class, args);
  }

  @Override
  public void run(String... args) {
    
    reactiveMongoTemplate.dropCollection("products")
            .subscribe();
    
    Flux.just(Product.builder().name("TV Panasonic").price(356.25).build(),
              Product.builder().name("Sony Camera").price(177.25).build(),
              Product.builder().name("Apple ipod").price(100.25).build(),
              Product.builder().name("Sony notebook").price(650.25).build(),
              Product.builder().name("HP computer").price(785.25).build(),
              Product.builder().name("TV LG").price(456.25).build(),
              Product.builder().name("Ferrari").price(2000.25).build(),
              Product.builder().name("Lambo").price(8476.25).build())
            .flatMap(product -> {
              product.setCreatedDate(new Date());
              return productRepository.save(product);
            })
            .subscribe(objectMono -> log.info("Insert: " + objectMono));
  }
}
