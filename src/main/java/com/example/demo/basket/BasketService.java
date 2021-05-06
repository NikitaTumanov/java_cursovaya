package com.example.demo.basket;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public void create(Basket u) {
        log.info("Save busket {}", u);
        basketRepository.save(u);
    }

    public List<Basket> readAll() {
        log.info("Find all buskets");
        return basketRepository.findAll();
    }

    public Basket findByBusketId(int id) {
        log.info("Find busket, whose id = {}", id);
        return basketRepository.findById(id);
    }

    public List<Basket> findByUser(User user) {
        log.info("Find busket, whose id = {}", user);
        return (List<Basket>) basketRepository.findAllByUser(user);
    }

    public Long deleteByUser(User user) {
        log.info("Find busket, whose id = {}", user);
        return basketRepository.deleteByUser(user);
    }

    public void delete(int id) {
        log.info("Delete  busket, whose id = {}", id);
        basketRepository.deleteById(id);
    }
}
