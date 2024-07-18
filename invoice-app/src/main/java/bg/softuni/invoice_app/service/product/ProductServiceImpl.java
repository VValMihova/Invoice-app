package bg.softuni.invoice_app.service.product;

import bg.softuni.invoice_app.exeption.NotFoundObjectException;
import bg.softuni.invoice_app.model.dto.product.ProductView;
import bg.softuni.invoice_app.model.entity.Product;
import bg.softuni.invoice_app.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;
  
  public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
    this.productRepository = productRepository;
    this.modelMapper = modelMapper;
  }
  
  @Override
  public void save(Product product) {
    this.productRepository.save(product);
  }
  
  @Override
  public List<ProductView> findAll(Long id) {
    Optional<List<Product>> productsList = this.productRepository.findAllByUserIdOrderByQuantity(id);
    if (productsList.isPresent()) {
      return productsList.get().stream().map(recipientDetails -> modelMapper.map(recipientDetails, ProductView.class)).toList();
    } else {
      throw new NotFoundObjectException("Product");
    }
  }
}
