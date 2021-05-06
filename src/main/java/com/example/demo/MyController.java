package com.example.demo;

import com.example.demo.basket.Basket;
import com.example.demo.basket.BasketService;
import com.example.demo.category.Category;
import com.example.demo.category.CategoryService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
import com.example.demo.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
public class MyController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    /*@Autowired
    private EmailService emailService;*/
    @Autowired
    private CriteriaService criteriaService;
    @Autowired
    private UserService userService;
    @Autowired
    private BasketService basketService;

    @RequestMapping(path = "/myinfo")
    public String myinfo(Authentication authentication,Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("user", userService.findByName(authentication.getName()));
        return "myinfo";
    }

    @SneakyThrows
    @PostMapping(path = "/myinfo")
    public String change_myinfo(Authentication authentication, String email, @RequestParam String action, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("user", userService.findByName(authentication.getName()));
        if ("change_email".equals(action)) {
            userService.findByName(authentication.getName()).setEmail(email);
            userService.saveChanges(userService.findByName(authentication.getName()));
        }
        return "myinfo";
    }

    @RequestMapping(path = "/admin")
    public String admin(Authentication authentication, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        if(userService.findByName(authentication.getName()).getType().equals("admin")){
            return "admin";
        }
        else{
            model.addAttribute("products", productService.readAll());
            return "products";
        }
    }

    @RequestMapping(path = "/categories")
    public String Categoriesinfo(Authentication authentication, Model model) throws IOException, MessagingException {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("categories", categoryService.readAll());
        return "categories";
    }
    @RequestMapping(path = "/users")
    public String Usersinfo(Authentication authentication, Model model) throws IOException, MessagingException {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("users", userService.readAll());
        return "users";
    }

    @SneakyThrows
    @PostMapping(path = "/products")
    public String addToBasket(Authentication authentication, String productName, int volume, @RequestParam String action, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("user", userService.findByName(authentication.getName()));
        model.addAttribute("products", productService.readAll());
        if ("add_to_basket".equals(action)) {
            Basket basket = new Basket();
            basket.setProduct(productService.findByProductName(productName));
            basket.setVolume(volume);
            basket.setUser(userService.findByName(authentication.getName()));
            userService.findByName(authentication.getName()).getBaskets().add(basket);
            productService.findByProductName(productName).getBaskets().add(basket);
            basketService.create(basket);
            productService.findByProductName(productName).setProductVolume(productService.findByProductName(productName).getProductVolume()-volume);
            productService.create(productService.findByProductName(productName));
        }
        return "products";
    }

    @RequestMapping(path = "/products")
    public String Productsinfo(Authentication authentication, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("products", productService.readAll());
        return "products";
    }

    @RequestMapping(path = "/basket")
    public String Basketinfo(Authentication authentication, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("baskets", basketService.findByUser(userService.findByName(authentication.getName())));
        model.addAttribute("productService", productService);
        return "basket";
    }

    @SneakyThrows
    @PostMapping(path = "/admin")
    public String add_delete(Authentication authentication, String name, Integer volume, Integer price, String category, String type, @RequestParam String action, Model model){
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
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
                prod.setProductPrice(price);
                prod.setCategory(categoryService.findByName(category));
                categoryService.findByName(category).getProducts().add(prod);
                productService.create(prod);
            }
            case "delete_product" -> {
                productService.findByProductName(name).getCategory().getProducts().remove(productService.findByProductName(name));
                productService.delete(name);
            }
            case "change_type" -> {
                userService.findByName(name).setType(type);
                userService.saveChanges(userService.findByName(name));
            }
            case "delete_user" -> {
                basketService.deleteByUser(userService.findByName(name));
                userService.delete(name);
            }
        }
        //emailService.sendmail(category);
        //model.addAttribute("categories", categoryService.readAll());
       return "admin";
    }

    @RequestMapping(value = "/products/takebycategory", method = RequestMethod.GET)
    public String takenByCategory(Authentication authentication, @RequestParam String category, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("products", criteriaService.takeByCategory(category));
        model.addAttribute("category", category);
        return "products/takebycategory";
    }

    @RequestMapping(value = "/products/takebyname", method = RequestMethod.POST)
    public String takenByProductName(Authentication authentication, @RequestParam String name, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("products", criteriaService.takeByProductName(name));
        model.addAttribute("name", name);
        return "products/takebyname";
    }

    @GetMapping("/sign")
    public String index() {
        return "signup";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index2(Authentication authentication, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("products", productService.readAll());
        return "products";
    }

    @RequestMapping(path = "/signUperror", method = RequestMethod.POST)
    public String SignUp(@RequestParam String username, String password, String password2, String email, String type, Model model) {
        if (!password.equals(password2)) {
            model.addAttribute("Status", "pass1!=pass2");
            return "signup";
        } else {
            if (userService.loadUserByUsername(username) != null) {
                model.addAttribute("Status", "user_exists");
                return "signup";
            } else {
                userService.create(username, password, email, type);
                return "redirect:/";
            }
        }
    }
}