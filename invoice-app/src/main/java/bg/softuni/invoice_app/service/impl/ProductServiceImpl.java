package bg.softuni.invoice_app.service.impl;

import bg.softuni.invoice_app.model.entity.Product;
import bg.softuni.invoice_app.repository.ProductRepository;
import bg.softuni.invoice_app.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }
  
  @Override
  public void save(Product product) {
    this.productRepository.save(product);
  }
}
