package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.backend.domain.Response;
import pl.edu.wat.backend.dtos.ReservationDto;
import pl.edu.wat.backend.services.NotificationService;
import pl.edu.wat.backend.services.NotificationServiceImpl;

@RestController
public class InvoiceController {
    private final NotificationService notificationService;

    @Autowired
    public InvoiceController(NotificationServiceImpl notificationServiceImpl) {
        this.notificationService = notificationServiceImpl;
    }
    @PostMapping("/invoice")
    public ResponseEntity<Response> generateInvoice(@RequestBody ReservationDto reservationDto) {
        try {
            notificationService.sendInvoiceNotification(reservationDto);
        } catch (MailException e) {
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response("Invoice generated successfully!"), HttpStatus.OK);
    }
}
