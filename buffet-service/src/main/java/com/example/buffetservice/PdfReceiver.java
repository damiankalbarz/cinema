package com.example.buffetservice;

import com.example.buffetservice.model.Invoice;
import com.example.buffetservice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Component
public class PdfReceiver {

    @Autowired
    private InvoiceRepository invoiceRepository;

    private static final String TOPIC = "invoice";

    @KafkaListener(topics = TOPIC)
    public void receive(byte[] pdfData) {

        Invoice invoice = new Invoice();
        invoice.setPdfData(pdfData);
        invoiceRepository.save(invoice);
        System.out.println("Odebrano plik PDF: " + pdfData.length + " bajtów");
        System.out.println("Dane PDF: " + Arrays.toString(pdfData));

        String filePath = "invoice.pdf";

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfData); // Zapisanie danych do pliku
            System.out.println("Plik PDF został pomyślnie zapisany: " + filePath);
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas zapisywania pliku PDF: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
