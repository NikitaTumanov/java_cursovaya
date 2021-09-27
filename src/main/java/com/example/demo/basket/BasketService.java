package com.example.demo.basket;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Класс работы с БД корзин.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BasketService {
    /**
     * Объект, реализующий методы репозитория корзин.
     */
    @Autowired
    private BasketRepository basketRepository;

    /**
     * Конструктор BasketService.
     * @param basketRepository Репозиторий корзин.
     */
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    /**
     * Метод сохранения новой корзины.
     * @param u Новосозданный объект корзины.
     */
    public void create(Basket u) {
        log.info("Save busket {}", u);
        basketRepository.save(u);
    }

    /**
     * Метод получения всех корзин.
     * @return Вернуть список всех корзин.
     */
    public List<Basket> readAll() {
        log.info("Find all buskets");
        return basketRepository.findAll();
    }

    /**
     * Метод получения корзины по id.
     * @param id id корзины.
     * @return Вернуть искомую корзину.
     */
    public Basket findByBusketId(long id) {
        log.info("Find busket, whose id = {}", id);
        return basketRepository.findById(id);
    }

    /**
     * Метод поиска всех корзин пользователя.
     * @param user Авторизованный в данный момент пользователь.
     * @return Вернуть список всех корзин пользователя.
     */
    public List<Basket> findByUser(User user) {
        log.info("Find busket, whose id = {}", user);
        return (List<Basket>) basketRepository.findAllByUser(user);
    }

    /**
     * Метод удаления всех корзин пользователя.
     * @param user Авторизованный в данный момент пользователь.
     * @return Вернуть положитльный ответ об удалении.
     */
    public Long deleteByUser(User user) {
        log.info("Find busket, whose id = {}", user);
        return basketRepository.deleteByUser(user);
    }

    /**
     * Метод удаления корзины по id.
     * @param id id корзины.
     */
    public void delete(long id) {
        log.info("Delete  busket, whose id = {}", id);
        basketRepository.deleteById(id);
    }
}
