package com.example.demo.basket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusketRepository extends JpaRepository<Busket, Integer> {
    Busket findById(long id);
    Long deleteById(long id);
}
