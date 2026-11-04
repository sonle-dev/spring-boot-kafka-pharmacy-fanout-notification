package com.example.inventoryservice.consumer;

import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.entity.Order;
import com.example.inventoryservice.event.OrderEvent;
import com.example.inventoryservice.repository.MedicineRepository;
import com.example.inventoryservice.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderEventConsumer {

    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;

    public OrderEventConsumer(MedicineRepository medicineRepository, OrderRepository orderRepository) {
        this.medicineRepository = medicineRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @KafkaListener(topics = "${app.kafka.topic.medicine-stock}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderEvent(OrderEvent event) {
        Medicine medicine = medicineRepository.findById(event.getMedicineId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc với ID: " + event.getMedicineId()));

        double totalAmount = medicine.getPrice() * event.getQuantity();
        Order order = new Order(
                medicine.getId(),
                medicine.getPrice(),
                event.getQuantity(),
                event.getTimestamp(),
                totalAmount
        );
        orderRepository.save(order);

        medicine.setQuantity(medicine.getQuantity() - event.getQuantity());
        medicineRepository.save(medicine);

        System.out.println("Thêm mới đơn hàng thành công !");
    }
}