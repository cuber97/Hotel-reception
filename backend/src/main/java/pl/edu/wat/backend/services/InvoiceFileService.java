package pl.edu.wat.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.ReservationDto;

import java.io.OutputStream;

@Service
public interface InvoiceFileService {
    void generateDocument(OutputStream outputStreamn, ReservationDto reservationDto);
}
