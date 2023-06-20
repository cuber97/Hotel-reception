package pl.edu.wat.backend.services;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CurrencyService {
    BigDecimal getPlnToEuroCurrency();
}
