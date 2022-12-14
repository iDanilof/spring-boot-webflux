package com.example.springboot.webflux.models.documents;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
@Builder
public class Product {
  
  @Id
  private String id;
  
  private String name;
  
  private Double price;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdDate;
}
