package pl.edu.wat.backend.services;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import pl.edu.wat.backend.dtos.AccountDto;
import pl.edu.wat.backend.dtos.ReservationDto;
import pl.edu.wat.backend.entities.AccountEntity;

public interface NotificationService {
    void sendRegistrationNotification(AccountDto accountDto) throws MailException;
    void sendInvoiceNotification(ReservationDto reservationDto);
}
