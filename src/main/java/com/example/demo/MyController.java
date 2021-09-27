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

/**
 * Класс, обрабатывающий операции на веб-ресурсе.
 */
@Controller
public class MyController {
    /**
     * Сервис работы с категориями.
     */
    @Autowired
    private CategoryService categoryService;
    /**
     * Сервис работы с продуктами.
     */
    @Autowired
    private ProductService productService;
    /**
     * Сервис работы с почтой.
     */
    @Autowired
    private EmailService emailService;
    /**
     * Сервис работы с фильтрацией.
     */
    @Autowired
    private CriteriaService criteriaService;
    /**
     * Сервис работы с пользователями.
     */
    @Autowired
    private UserService userService;
    /**
     * Сервис работы с корзинами.
     */
    @Autowired
    private BasketService basketService;

    /**
     * Метод вывода информации о пользователе на странице myinfo.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Вывести model на страницу myinfo.
     */
    @RequestMapping(path = "/myinfo")
    public String myinfo(Authentication authentication,Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("user", userService.findByName(authentication.getName()));
        model.addAttribute("myName", authentication.getName());
        return "myinfo";
    }

    /**
     * Метод смены информации о пользователе на странице myinfo.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param email Почта пользователя.
     * @param action Название операции на странице.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу myinfo.
     */
    @SneakyThrows
    @PostMapping(path = "/myinfo")
    public String change_myinfo(Authentication authentication, String email, @RequestParam String action, Model model) {
        if ("change_email".equals(action)) {
            userService.findByName(authentication.getName()).setEmail(email);
            userService.saveChanges(userService.findByName(authentication.getName()));
        }
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("user", userService.findByName(authentication.getName()));
        model.addAttribute("myName", authentication.getName());
        return "myinfo";
    }

    /**
     * Метод перехода на страницу админа. Если пользователь не имеет прав, он попадает на страницу продуктов.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу admin или products в зависимости от типа пользователя.
     */
    @RequestMapping(path = "/admin")
    public String admin(Authentication authentication, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("myName", authentication.getName());
        if(userService.findByName(authentication.getName()).getType().equals("admin")){
            return "admin";
        }
        else{
            model.addAttribute("products", productService.readAll());
            return "products";
        }
    }

    /**
     * Метод вывода информации о категориях на странице categories.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу categories.
     * @throws IOException - Общий класс исключений, создаваемых неудачными или прерванными операциями ввода-вывода.
     * @throws MessagingException - Базовый класс для всех исключений, создаваемых классами обмена сообщениями.
     */
    @RequestMapping(path = "/categories")
    public String Categoriesinfo(Authentication authentication, Model model) throws IOException, MessagingException {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("categories", categoryService.readAll());
        model.addAttribute("myName", authentication.getName());
        return "categories";
    }

    /**
     * Метод вывода информации о всех пользователях на странице users.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу users.
     * @throws IOException - Общий класс исключений, создаваемых неудачными или прерванными операциями ввода-вывода.
     * @throws MessagingException - Базовый класс для всех исключений, создаваемых классами обмена сообщениями.
     */
    @RequestMapping(path = "/users")
    public String Usersinfo(Authentication authentication, Model model) throws IOException, MessagingException {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("users", userService.readAll());
        model.addAttribute("myName", authentication.getName());
        return "users";
    }

    /**
     * Метод реализует операции над корзиной: удаление товаров из нее, изменение количества товаров в ней.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param basketId id корзины.
     * @param volume Количество товара.
     * @param action Название операции на странице.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу basket.
     */
    @SneakyThrows
    @PostMapping(path = "/basket")
    public String BasketWork(Authentication authentication, long basketId, Integer volume, @RequestParam String action, Model model) {
        model.addAttribute("productService", productService);
        model.addAttribute("myName", authentication.getName());
        if ("delete_from_basket".equals(action)) {
            productService.findByProductName(basketService.findByBusketId(basketId).getProduct().getProductName())
                    .setProductVolume(productService.findByProductName(basketService.findByBusketId(basketId)
                            .getProduct().getProductName()).getProductVolume()+volume);
            productService.create(productService.findByProductName(basketService.findByBusketId(basketId).getProduct().getProductName()));
            basketService.findByBusketId(basketId).getProduct().getBaskets().remove(basketService.findByBusketId(basketId));
            basketService.findByBusketId(basketId).getUser().getBaskets().remove(basketService.findByBusketId(basketId));
            basketService.delete(basketId);
        }
        if ("make_order".equals(action)) {
            StringBuilder message = new StringBuilder();
            for(Basket basket:criteriaService.takeByUser(authentication.getName())){
                message.append(basket.toString());
                basket.getProduct().getBaskets().remove(basket);
                basket.getUser().getBaskets().remove(basket);
                basketService.delete(basket.getId());
            }
            emailService.sendmail(message.toString(), userService.findByName(authentication.getName()).getEmail());
        }
        int sum=0;
        for(Basket basket:criteriaService.takeByUser(authentication.getName())){
            sum+=basket.getVolume()*basket.getProduct().getProductPrice();
        }
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("user", userService.findByName(authentication.getName()));
        model.addAttribute("sum", sum);
        model.addAttribute("baskets", criteriaService.takeByUser(authentication.getName()));
        return "basket";
    }

    /**
     * Метод добавления товаров в корзину на странице products.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param productName Название товара.
     * @param volume Количество товара.
     * @param action Название операции на странице.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу products.
     */
    @SneakyThrows
    @PostMapping(path = "/products")
    public String addToBasket(Authentication authentication, String productName, int volume,
                              @RequestParam String action, Model model) {
        if ("add_to_basket".equals(action)) {
            Basket basket = new Basket();
            basket.setProduct(productService.findByProductName(productName));
            basket.setVolume(volume);
            basket.setUser(userService.findByName(authentication.getName()));
            userService.findByName(authentication.getName()).getBaskets().add(basket);
            productService.findByProductName(productName).getBaskets().add(basket);
            basketService.create(basket);
            productService.findByProductName(productName).setProductVolume(productService.findByProductName(productName)
                    .getProductVolume()-volume);
            productService.create(productService.findByProductName(productName));
        }
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("myName", authentication.getName());
        model.addAttribute("user", userService.findByName(authentication.getName()));
        model.addAttribute("products", productService.readAll());
        return "products";
    }

    /**
     * Метод вывода информации о всех товарах на странице products.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу products.
     */
    @RequestMapping(path = "/products")
    public String Productsinfo(Authentication authentication, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("myName", authentication.getName());
        model.addAttribute("products", productService.readAll());
        return "products";
    }

    /**
     * Метод вывода информации о корзине пользователя на странице basket.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу basket.
     */
    @RequestMapping(path = "/basket")
    public String Basketinfo(Authentication authentication, Model model) {
        int sum=0;
        for(Basket basket:criteriaService.takeByUser(authentication.getName())){
            sum+=basket.getVolume()*basket.getProduct().getProductPrice();
        }
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("baskets", criteriaService.takeByUser(authentication.getName()));
        model.addAttribute("myName", authentication.getName());
        model.addAttribute("productService", productService);
        model.addAttribute("sum", sum);
        return "basket";
    }

    /**
     * Метод, реализующий операции админа.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param name Имя пользователя, категории или товара.
     * @param volume Количество товаров.
     * @param price Цена товара.
     * @param category Название категории.
     * @param type Тип пользователя.
     * @param action Название операции на странице.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу admin.
     */
    @SneakyThrows
    @PostMapping(path = "/admin")
    public String add_delete(Authentication authentication, String name, Integer volume, Integer price, String category,
                             String type, @RequestParam String action, Model model){
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("myName", authentication.getName());
        switch (action) {
            case "add" -> {
                Category cat = new Category();
                cat.setName(name);
                categoryService.create(cat);
            }
            case "delete" -> {
                categoryService.delete(name);
            }
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
                productService.findByProductName(name).getCategory().getProducts().remove(productService.
                        findByProductName(name));
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
       return "admin";
    }

    /**
     * Метод вывода всех товаров конкретной категории со страницы products.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param category Название категории.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу products/takebycategory.
     */
    @RequestMapping(value = "/products/takebycategory", method = RequestMethod.GET)
    public String takenByCategory(Authentication authentication, @RequestParam String category, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("products", criteriaService.takeByCategory(category));
        model.addAttribute("category", category);
        model.addAttribute("myName", authentication.getName());
        return "products/takebycategory";
    }

    /**
     * Метод вывода всех товаров с конкретным названием со страницы products.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param name Название товара.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу products/takebyname.
     */
    @RequestMapping(value = "/products/takebyname", method = RequestMethod.POST)
    public String takenByProductName(Authentication authentication, @RequestParam String name, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("products", criteriaService.takeByProductName(name));
        model.addAttribute("name", name);
        model.addAttribute("myName", authentication.getName());
        return "products/takebyname";
    }

    /**
     * Метод, перенаправляющий пользователя на регистрацию
     * @return Перевод пользователя на страницу signup.
     */
    @GetMapping("/sign")
    public String index() {
        return "signup";
    }
    /**
     * Метод входа на веб-ресурс.
     * @param authentication Авторизованный в данный момент пользователь.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return Перевод пользователя на страницу products.
     */
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index2(Authentication authentication, Model model) {
        model.addAttribute("type", userService.findByName(authentication.getName()).getType());
        model.addAttribute("products", productService.readAll());
        model.addAttribute("myName", authentication.getName());
        return "products";
    }

    /**
     * Метод регистрации нового пользователя.
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @param password2 Повторный ввод пользователя.
     * @param email Почта пользователя.
     * @param type Тип пользователя.
     * @param model Объект для записи информации на страницу веб-ресурса.
     * @return - Перевод пользователя на страницу signup при ошибке или на страницу products при отсутствии ошибок.
     */
    @RequestMapping(path = "/signUperror", method = RequestMethod.POST)
    public String SignUp(@RequestParam String username, String password, String password2, String email, String type,
                         Model model) {
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