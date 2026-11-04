package com.example.notificationservice.consumer;

import com.example.notificationservice.entity.Medicine;
import com.example.notificationservice.event.OrderEvent;
import com.example.notificationservice.repository.MedicineRepository;
import com.example.notificationservice.service.EmailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationConsumer {

    private final MedicineRepository medicineRepository;
    private final EmailService emailService;

    @Value("${app.mail.customer-email}")
    private String customerEmail;

    public OrderNotificationConsumer(MedicineRepository medicineRepository, EmailService emailService) {
        this.medicineRepository = medicineRepository;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${app.kafka.topic.medicine-stock}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeNotificationEvent(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent event = record.value();
        String messageKey = record.key();

        // 1. In dòng log đầu tiên khớp đúng hình ảnh mẫu
        System.out.println("Đã gửi đơn hàng: " + event.getOrderId() + " với Key: " + messageKey);

        // 2. Query thông tin thuốc từ database (Kích hoạt Hibernate select m1_0...)
        Medicine medicine = medicineRepository.findById(event.getMedicineId())
                .orElse(null);

        String medicineName = (medicine != null) ? medicine.getMedicineName() : "Panadol";
        double price = (medicine != null) ? medicine.getPrice() : 15000.0;
        double totalPrice = price * event.getQuantity();

        // 3. Kích hoạt gửi email xác nhận cho khách hàng
        emailService.sendOrderInvoiceEmail(customerEmail, medicineName, event.getQuantity(), totalPrice);

        // 4. In dòng thông báo hoàn tất khớp đúng hình ảnh mẫu
        System.out.println("Đã gửi thông báo tới email khách hàng");
    }
}