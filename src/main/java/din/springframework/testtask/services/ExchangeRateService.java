package din.springframework.testtask.services;

import din.springframework.testtask.model.ExchangeRate;
import din.springframework.testtask.repositories.ExchangeRateRepository;

import java.time.LocalDate;

public interface ExchangeRateService extends ExchangeRateRepository {

    boolean existsByDate(LocalDate date);

    void deleteAllByDate(LocalDate date);

    ExchangeRate findFirstByCurrency_IdOrderByDateDesc(String id);
}
