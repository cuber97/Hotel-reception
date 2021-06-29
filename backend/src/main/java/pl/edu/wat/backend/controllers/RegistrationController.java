package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.backend.domain.Response;
import pl.edu.wat.backend.dtos.AccountDto;
import pl.edu.wat.backend.dtos.ReservationDto;
import pl.edu.wat.backend.exceptions.AccountConflictException;
import pl.edu.wat.backend.services.AccountService;
import pl.edu.wat.backend.services.NotificationService;
import pl.edu.wat.backend.services.NotificationServiceImpl;

@RestController
public class RegistrationController {
    private AccountService accountService;
    private NotificationService notificationService;

    @Autowired
    public RegistrationController(AccountService accountService, NotificationServiceImpl notificationServiceImpl) {
        this.accountService = accountService;
        this.notificationService = notificationServiceImpl;
    }

    @PostMapping("/registration")
    public ResponseEntity<Response> registration(@RequestBody AccountDto account) {
        if (account != null) {
            try {
                accountService.saveAccount(account);
//            notificationService.sendRegistrationNotification(accountEntity);
            } catch (AccountConflictException e) {
                return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.CONFLICT);
            }
            catch (MailException e) {
                return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.OK);
            }
            return new ResponseEntity<Response>(new Response("User is saved successfully"), HttpStatus.OK);
        }
        return null;
    }
}
