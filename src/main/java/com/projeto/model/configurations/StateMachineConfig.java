package com.projeto.model.configurations;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import com.projeto.model.enums.OrderEvents;
import com.projeto.model.enums.OrderStatus;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatus, OrderEvents> {

	@Override
	public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvents> states) throws Exception {
		states
			.withStates()
			.initial(OrderStatus.RECEIVED)
			.states(EnumSet.allOf(OrderStatus.class))
			.end(OrderStatus.COMPLETED)
			.end(OrderStatus.CANCELLED);
	}

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvents> transitions) throws Exception {
        transitions
            .withExternal()
                .source(OrderStatus.RECEIVED).target(OrderStatus.ACCEPTED).event(OrderEvents.VALIDATE)
                .action(validateOrderAction())
            .and()
            .withExternal()
                .source(OrderStatus.ACCEPTED).target(OrderStatus.PAID).event(OrderEvents.PAY)
                .action(paymentOrderAction())
            .and()
            .withExternal()
            	.source(OrderStatus.PAID).target(OrderStatus.SENT).event(OrderEvents.SHIP)
            .and()
            .withExternal()
        		.source(OrderStatus.SENT).target(OrderStatus.COMPLETED).event(OrderEvents.COMPLETE)
            .and()
            .withExternal()
            	.source(OrderStatus.RECEIVED).target(OrderStatus.CANCELLED).event(OrderEvents.CANCEL);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderEvents> config) throws Exception{
    	config.withConfiguration().listener(stateMachineListener());
    }
    
    @Bean
    public StateMachineListener<OrderStatus, OrderEvents> stateMachineListener(){
    	return new StateMachineListenerAdapter<>() {
    		@Override
    		public void transition(Transition<OrderStatus, OrderEvents> transition) {
    			if(transition.getTarget().getId() != null){
        			System.out.println("Transtion from " 
        					+ (transition.getSource() != null ? transition.getSource().getId() : "none") 
        					+ " to " 
        					+ transition.getTarget().getId());
    			}
    		}
    	};
    }
    
    
    
    @Bean
    public Action<OrderStatus, OrderEvents> paymentOrderAction() {
		return context ->{
			System.out.println("Payment order");
		};
	}

    @Bean
    public Action<OrderStatus, OrderEvents> validateOrderAction() {
		return context ->{
			System.out.println("Order Validate e acept");
		};
	}
}
