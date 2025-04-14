package com.MicroServicesproject.notificationService.service;

import com.MicroServicesproject.notificationService.order.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class notificationService {
    private final JavaMailSender javaMailSender;
    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("XYZ@email.com");
            mimeMessageHelper.setTo(orderPlacedEvent.getEmail().toString());
            mimeMessageHelper.setSubject(String.format("Order %s placed", orderPlacedEvent.getOrderNumber()));
            mimeMessageHelper.setText(String.format("""
                    Hi,
                    Order is placed.
                    """));
        };
        try{
            javaMailSender.send(mimeMessagePreparator);
            log.info("Mail Sent");
        } catch (MailException e){
            log.error("Error when sending mail", e);
            throw new RuntimeException("Exception when sending email", e);
        }

    }
}
