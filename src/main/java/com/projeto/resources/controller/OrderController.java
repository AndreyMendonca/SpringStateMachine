package com.projeto.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.model.entity.Order;
import com.projeto.services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService service;
	
	@PostMapping("new")
	@ResponseStatus(HttpStatus.OK)
	public String newOrder(@RequestBody Order order) {
		String status = service.newOrder(order);
		return "New Order - OrderStatus = " + status;
	}
	
	@PostMapping("pay")
	public String payOrder() {
		return "payment Order";
	}
	
	@PostMapping("sent")
	public String sentOrder() {
		service.shippimentOrder();
		return "ship Order";
	}
	
	@PostMapping("complet")
	public String finishOrder() {
		service.completOrder();
		return "Complet Order";
	}
	
	/*
	public OrderController(OrderService service) {
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Order save(@RequestBody Order order) {
		return service.save(order);
	} */
}
