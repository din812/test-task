package din.springframework.testtask.bootstrap;

import din.springframework.testtask.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class Bootstrap implements CommandLineRunner {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public Bootstrap(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
    }


    /**
     * This class and specifically this method used for getting XML and parsing it via DataSourceParser.
     */
    @Override
    public void run(String... args) {
        new DataSourceParser(exchangeRateRepository, currencyRepository); //pull today's data from CBR.ru site
    }

}
