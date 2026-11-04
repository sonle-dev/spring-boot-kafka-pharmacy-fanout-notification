package com.example.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderInvoiceEmail(String toEmail, String medicineName, int quantity, double totalPrice) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Xác nhận đơn hàng");

            // Format nội dung email đúng như hình ảnh mẫu
            String emailContent = String.format(
                    "Cảm ơn bạn đã đặt hàng tại Pharma Medicine:\n" +
                            "Tên thuốc : %s\n" +
                            "Số lượng : %d\n" +
                            "Tổng tiền : %.6f",
                    medicineName, quantity, totalPrice
            );

            message.setText(emailContent);
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Lỗi khi gửi email: {}", ex.getMessage());
        }
    }
}