package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.AccountDto;
import pl.edu.wat.backend.dtos.ReservationDto;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;

@Service
public class NotificationServiceImpl implements NotificationService{
    private final JavaMailSender javaMailSender;
    private final InvoiceFileService invoiceFileService;

    @Value("${spring.mail.username}")
    String fromMail;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender, InvoiceFileService invoiceFileService) {
        this.javaMailSender = javaMailSender;
        this.invoiceFileService = invoiceFileService;
    }

    public void sendRegistrationNotification(AccountDto accountDto) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(accountDto.getEmail());
        mail.setFrom(fromMail);
        mail.setSubject("Konto w aplikacji hotelowej");
        mail.setText(accountDto.getFirstName() + ", Twoje konto w aplikcaji hotelowej zostało utworzone!");
        javaMailSender.send(mail);
    }

    public void sendInvoiceNotification(ReservationDto reservationDto) {
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(reservationDto.getCustomer().getEmail()));
            mimeMessage.setFrom(new InternetAddress(fromMail));
            mimeMessage.setSubject("Faktura za rezerwację");

            ByteArrayOutputStream outputStream;
            outputStream = new ByteArrayOutputStream();
            invoiceFileService.generateDocument(outputStream, reservationDto);
            byte[] bytes = outputStream.toByteArray();

            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.addAttachment("Faktura.pdf", dataSource);
            helper.setText(reservationDto.getCustomer().getFirstName() + " w załączniku faktura za pobyt w hotelu. Serdecznie dziękujemy!");
        };

        javaMailSender.send(preparator);
    }
}
