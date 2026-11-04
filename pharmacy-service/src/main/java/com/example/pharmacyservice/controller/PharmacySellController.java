package com.example.pharmacyservice.controller;

import com.example.pharmacyservice.event.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacySellController {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.topic.medicine-stock}")
    private String topicName;

    public PharmacySellController(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellMedicine(@RequestParam("medicineId") Long medicineId,
                                               @RequestParam("quantity") int quantity) {
        String orderId = UUID.randomUUID().toString();
        OrderEvent event = new OrderEvent(orderId, medicineId, quantity);

        // Gửi sang Kafka với Key là medicineId dạng String
        kafkaTemplate.send(topicName, String.valueOf(medicineId), event);

        return ResponseEntity.ok("Thanh toán thành công! Sự kiện đã được gửi tới Kafka.");
    }
}