package com.projeto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.model.entity.Order;
import com.projeto.model.repository.OrderRepository;

@Service
public class OrderServiceCRUD {

	
	@Autowired
	private OrderRepository repository;
	
	public Order save(Order order) {
		return repository.save(order);
	}
	
	public Optional<Order> findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Order> findAll(){
		return repository.findAll();
	}
}
