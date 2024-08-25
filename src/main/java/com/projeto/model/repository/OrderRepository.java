package com.projeto.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
