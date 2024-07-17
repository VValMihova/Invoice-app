package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.service.product.ProductService;
import bg.softuni.invoice_app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;
  private final UserService userService;
  
  public ProductController(ProductService productService, UserService userService) {
    this.productService = productService;
    this.userService = userService;
  }
  
  @GetMapping
  public String productsPage(Model model) {
    model.addAttribute("products", productService.findAll(userService.getCurrentUserId()));
    return "products";
  }
}
