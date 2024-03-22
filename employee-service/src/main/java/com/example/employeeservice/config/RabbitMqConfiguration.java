package com.example.employeeservice.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitMqConfiguration {

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    private final AmqpAdmin rabbitAdmin;

    public RabbitMqConfiguration(AmqpAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }



    public void declareQueue() {
        String name = "my-queue";
        String routingKey = "my-key";
        boolean durable = true;
        boolean exclusive = false;
        boolean autoDelete = false;


        String queueName = rabbitAdmin.declareQueue(new Queue(name, durable, exclusive, autoDelete));

        String exchangeName = "my-exchange";
        DirectExchange exchange = new DirectExchange(exchangeName, durable, autoDelete);
        rabbitAdmin.declareExchange(exchange);

        Binding.DestinationType destinationType = Binding.DestinationType.QUEUE;
        Map<String, Object> arguments = null;
        Binding binding = new Binding(queueName, destinationType, exchangeName, routingKey,
            arguments);
        rabbitAdmin.declareBinding(binding);
    }
}