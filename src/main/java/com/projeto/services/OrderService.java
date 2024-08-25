package com.projeto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import com.projeto.model.entity.Order;
import com.projeto.model.enums.OrderEvents;
import com.projeto.model.enums.OrderStatus;
import com.projeto.model.repository.OrderRepository;

import reactor.core.publisher.Mono;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private StateMachineFactory<OrderStatus, OrderEvents> stateMachineFactory;
	private StateMachine<OrderStatus, OrderEvents> stateMachine;

	public String newOrder(Order order) {
		startOrder();
		validateOrder();
		repository.save(order);
		return order.getOrderStatus().toString();
	}
	
	private void startOrder() {
		System.out.println("Start Order saga");
		stateMachine = stateMachineFactory.getStateMachine();
		stateMachine.startReactively().subscribe();
		System.out.println("Final state: " + stateMachine.getState().getId());
	}
	
	private void validateOrder() {
		System.out.println("Validade order");
		stateMachine.sendEvent(Mono.just(
				MessageBuilder.withPayload(OrderEvents.VALIDATE).build()))
				.subscribe(result -> System.out.println(result.getResultType()));
		System.out.println("Final state: " + stateMachine.getState().getId());
	}
	
	public void paymentOrder() {
		System.out.println("Payment order");
		stateMachine.sendEvent(Mono.just(
				MessageBuilder.withPayload(OrderEvents.PAY).build()))
				.subscribe(result -> System.out.println(result.getResultType()));
		System.out.println("Final state: " + stateMachine.getState().getId());
	}
	
	public void shippimentOrder() {
		System.out.println("Ship order");
		stateMachine.sendEvent(Mono.just(
				MessageBuilder.withPayload(OrderEvents.SHIP).build()))
				.subscribe(result -> System.out.println(result.getResultType()));
		System.out.println("Final state: " + stateMachine.getState().getId());
	}
	
	public void completOrder() {
		System.out.println("Stopping order");
		stateMachine.stopReactively().subscribe();
	}
}
