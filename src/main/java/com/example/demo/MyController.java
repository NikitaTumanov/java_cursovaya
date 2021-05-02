package com.example.demo;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
import com.example.demo.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Controller
public class MyController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    /*@Autowired
    private EmailService emailService;*/
    @Autowired
    private CriteriaService cus;
    @Autowired
    private UserService uu;

    @RequestMapping(path = "/home")
    public String myinfo() {
        return "myinfo";
    }

    @RequestMapping(path = "/categories")
    public String Categoriesinfo(Model model) throws IOException, MessagingException {
        model.addAttribute("categories", categoryService.readAll());
        return "categories";
    }

    @RequestMapping(path = "/products")
    public String Productsinfo(Model model) {
        model.addAttribute("products", productService.readAll());
        return "products";
    }

    @SneakyThrows
    @GetMapping(path = "/add_category")
    public String addNewCategory(@RequestParam String name, Model model) {
        Category category = new Category();
        category.setName(name);
        categoryService.create(category);
        //emailService.sendmail(category);
        model.addAttribute("categories", categoryService.readAll());
        return "categories";
    }

    @SneakyThrows
    @GetMapping(path = "/add_product")
    public String addNewProduct(@RequestParam String name, @RequestParam int volume, @RequestParam int price,
                                @RequestParam String category, Model model) {
        Product product = new Product();
        product.setProductName(name);
        product.setProductVolume(volume);
        product.setProductVolume(price);
        product.setCategory(categoryService.findByName(category));
        categoryService.findByName(category).getProducts().add(product);
        productService.create(product);
        //emailService.sendmail(product);
        model.addAttribute("products", productService.readAll());
        return "products";
    }

    @GetMapping(path = "/delete_category")
    public String DeleteBank(@RequestParam String name, Model model) {
        categoryService.delete(name);
        model.addAttribute("categories", categoryService.readAll());
        return "categories";
    }

    @GetMapping(path = "/delete_product")
    public String DeleteProduct(@RequestParam String name, Model model) {
        productService.findByProductName(name).getCategory().getProducts().remove(productService.findByProductName(name));
        productService.delete(name);
        model.addAttribute("products", productService.readAll());
        return "products";
    }

    @RequestMapping(value = "/categories/takebyname", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> takenByName() {
        return new ResponseEntity<>(cus.takeByName(), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/takebyproductName", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> takenByProductName() {
        return new ResponseEntity<>(cus.takeByProductName(), HttpStatus.OK);
    }

    @RequestMapping(value = "/cards/takebycode", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> takenByCategory_id() {
        return new ResponseEntity<>(cus.takeByCategory_id(), HttpStatus.OK);
    }

    @GetMapping("/sign")
    public String index() {
        return "signup";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index2(Authentication authentication, Model model) {
        model.addAttribute("user_name", "Добро пожаловать " + authentication.getName());
        return "hello";
    }

    @RequestMapping(path = "/signUperror", method = RequestMethod.POST)
    public String SignUp(@RequestParam String username, String password, String password2, Model model) {
        if (!password.equals(password2)) {
            model.addAttribute("Status", "pass1!=pass2");
            return "signup";
        } else {
            if (uu.loadUserByUsername(username) != null) {
                model.addAttribute("Status", "user_exists");
                return "signup";
            } else {
                uu.create(username, password);
                return "redirect:/";
            }
        }
    }
}