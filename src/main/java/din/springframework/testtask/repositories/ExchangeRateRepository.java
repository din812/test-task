package din.springframework.testtask.repositories;

import din.springframework.testtask.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, UUID> {
    boolean existsByDate(LocalDate date);

    void deleteAllByDate(LocalDate date);

    ExchangeRate findFirstByCurrency_IdOrderByDateDesc(String id);
}

