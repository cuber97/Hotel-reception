package pl.edu.wat.backend.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.wat.backend.exceptions.CurrencyRateNotAvailableException;
import pl.edu.wat.backend.responses.NbpExchangeRateEuroToPlnResponse;

import java.math.BigDecimal;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Value("${externalApi.nbpRatesApiUrl}")
    private String npbRatesApiUrl;

    public BigDecimal getPlnToEuroCurrency() throws CurrencyRateNotAvailableException {
        RestTemplate restTemplate = new RestTemplate();
        NbpExchangeRateEuroToPlnResponse nbpExchangeRateEuroToPlnResponse = restTemplate.getForObject(this.npbRatesApiUrl, NbpExchangeRateEuroToPlnResponse.class);
        if (nbpExchangeRateEuroToPlnResponse == null) {
            throw new CurrencyRateNotAvailableException("Currency rate are not available!");
        }
        return new BigDecimal(nbpExchangeRateEuroToPlnResponse.rates.get(0).bid);
    }
}
