package com.example.springboot.webflux.controller;

import java.time.Duration;

import com.example.springboot.webflux.models.dao.ProductRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

@Controller
@Slf4j
public class ProductController {
  
  @Autowired
  private ProductRepository productRepository;
  
  @GetMapping({"/list", "/"})
  public String list(Model model) {
    
    final var products = productRepository.findAll()
            .map(product -> {
              product.setName(product.getName().toUpperCase());
              return product;
            });
    
    products.subscribe(product -> log.info(product.getName()));
    
    model.addAttribute("products", products);
    model.addAttribute("title", "List of Products");
    
    return "list";
  }
  
}
