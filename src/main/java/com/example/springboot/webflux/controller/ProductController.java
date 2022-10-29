package com.example.springboot.webflux.controller;

import java.time.Duration;

import com.example.springboot.webflux.models.documents.Product;
import com.example.springboot.webflux.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {
  
  private final ProductService productService;
  
  @GetMapping({"/list", "/"})
  public Mono<String> list(Model model) {
    
    final var products = productService.findAllNamesUppercase();
    
    products.subscribe(product -> log.info(product.getName()));
    
    model.addAttribute("products", products);
    model.addAttribute("title", "List of Products");
    
    return Mono.just("list");
  }
  
  @GetMapping("/form")
  public Mono<String> create(Model model) {
    model.addAttribute("product", new Product());
    model.addAttribute("title", "Product form");
    return Mono.just("form");
  }
  
  @PostMapping("/form")
  public Mono<String> save(Product product) {
    return productService.save(product).doOnNext(product1 -> 
        log.info("Product saved: " + product1.getName() + "Id: " + product1.getId()))
        //.then(Mono.just("redirect:/list)) We can use it too
        .thenReturn("redirect:/list");
  }

  @GetMapping("/list-data-driver")
  public String listDataDriver(Model model) {

    final var products = productService.findAllNamesUppercase()
            .delayElements(Duration.ofSeconds(1));

    products.subscribe(product -> log.info(product.getName()));

    //How to contrarrest the back-pressure with Thymeleaf and Flux
    //We declare the buffe size and show by N elements.
    model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 2));
    model.addAttribute("title", "List of Products");

    return "list";
  }

  @GetMapping("/list-full")
  public String listFull(Model model) {

    final var products = productService.findAllNamesUppercaseWithRepeat();

    products.subscribe(product -> log.info(product.getName()));

    model.addAttribute("products", products);
    model.addAttribute("title", "List of Products");

    return "list";
  }

  @GetMapping("/list-chunked")
  public String listChunked(Model model) {

    final var products = productService.findAllNamesUppercaseWithRepeat();

    products.subscribe(product -> log.info(product.getName()));

    model.addAttribute("products", products);
    model.addAttribute("title", "List of Products");

    return "list-chunked";
  }
  
}
