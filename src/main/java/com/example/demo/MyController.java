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
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/main")
    public String main() {
        return "products";
    }

    @RequestMapping(path = "/myinfo")
    public String myinfo() {
        return "myinfo";
    }

    @RequestMapping(path = "/admin")
    public String admin() {
        return "admin";
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
    @PostMapping(path = "/admin")
    public String add_delete(String name, Integer volume, Integer price, String category, @RequestParam String action){
        switch (action) {
            case "add" -> {
                Category cat = new Category();
                cat.setName(name);
                categoryService.create(cat);
            }
            case "delete" -> categoryService.delete(name);
            case "add_product" -> {
                Product prod = new Product();
                prod.setProductName(name);
                prod.setProductVolume(volume);
                prod.setProductVolume(price);
                prod.setCategory(categoryService.findByName(category));
                categoryService.findByName(category).getProducts().add(prod);
                productService.create(prod);
            }
            case "delete_product" -> {
                productService.findByProductName(name).getCategory().getProducts().remove(productService.findByProductName(name));
                productService.delete(name);
            }
        }
        //emailService.sendmail(category);
        //model.addAttribute("categories", categoryService.readAll());
       return "admin";
    }

    @RequestMapping(value = "/products/takebycategory", method = RequestMethod.GET)
    public String takenByCategory(@RequestParam String category, Model model) {
        model.addAttribute("products", cus.takeByCategory(category));
        return "products/takebycategory";
    }

    @RequestMapping(value = "/products/takebyname", method = RequestMethod.POST)
    public String takenByProductName(@RequestParam String name, Model model) {
        model.addAttribute("products", cus.takeByProductName(name));
        return "products/takebyname";
    }

    @GetMapping("/sign")
    public String index() {
        return "signup";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index2(Authentication authentication, Model model) {
        model.addAttribute("user_name", "Добро пожаловать " + authentication.getName());
        return "products";
    }

    @RequestMapping(path = "/signUperror", method = RequestMethod.POST)
    public String SignUp(@RequestParam String username, String password, String password2, String email, Model model) {
        if (!password.equals(password2)) {
            model.addAttribute("Status", "pass1!=pass2");
            return "signup";
        } else {
            if (uu.loadUserByUsername(username) != null) {
                model.addAttribute("Status", "user_exists");
                return "signup";
            } else {
                uu.create(username, password, email);
                return "redirect:/";
            }
        }
    }
}