package bg.softuni.invoice_app.web;

import bg.softuni.invoice_app.service.product.ProductService;
import bg.softuni.invoice_app.service.user.UserHelperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
  private final UserHelperService userHelperService;
  private final ProductService productService;
  
  public ProductController(UserHelperService userHelperService, ProductService productService) {
    this.userHelperService = userHelperService;
    this.productService = productService;
  }
  
  @GetMapping
  public String productsPage(Model model) {
    model.addAttribute("products", productService.findAll(userHelperService.getUser().getId()));
    return "products";
  }
}
