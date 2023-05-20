package ru.job4j.kitchen.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Order;

@Service
@Slf4j
@AllArgsConstructor
public class KitchenService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "preorder")
    public void receiveOrder(Order order) {
        log.debug(order.toString());
        kafkaTemplate.send("cooked_order", getDish(order.getDishId()));
    }

    private String getDish(int dishId) {
        if (dishId % 2 == 0) {
            return "Готов к выдаче";
        }
        return "Нет такого блюда";
    }
}