package com.example.demo.basket;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
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
public class BusketService {
    @Autowired
    private BusketRepository busketRepository;

    public BusketService(BusketRepository busketRepository) {
        this.busketRepository = busketRepository;
    }

    public void create(Busket u) {
        log.info("Save busket {}", u);
        busketRepository.save(u);
    }

    public List<Busket> readAll() {
        log.info("Find all buskets");
        return busketRepository.findAll();
    }

    public Busket findByBusketId(int id) {
        log.info("Find busket, whose id = {}", id);
        return busketRepository.findById(id);
    }

    public void delete(int id) {
        log.info("Delete  busket, whose id = {}", id);
        busketRepository.deleteById(id);
    }
}
