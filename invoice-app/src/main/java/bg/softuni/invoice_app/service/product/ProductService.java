package bg.softuni.invoice_app.service.product;

import bg.softuni.invoice_app.model.dto.product.ProductView;
import bg.softuni.invoice_app.model.entity.Product;

import java.util.List;

public interface ProductService {
  void save(Product product);
  List<ProductView> findAll(Long id);
}
