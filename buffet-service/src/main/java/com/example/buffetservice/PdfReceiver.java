package com.example.buffetservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PdfReceiver {

    private static final String TOPIC = "invoice";

    @KafkaListener(topics = TOPIC)
    public void receive(byte[] pdfData) {
        System.out.println("Odebrano plik PDF: " + pdfData.length + " bajtów");
    }
}
