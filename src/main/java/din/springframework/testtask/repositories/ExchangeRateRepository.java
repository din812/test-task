package din.springframework.testtask.repositories;

import din.springframework.testtask.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, UUID> {
    /**
     * Checks if data base contains data by date
     */
    boolean existsByDate(LocalDate date);

    /**
     * Deletes all rows by date
     */
    void deleteAllByDate(LocalDate date);

    /**
     * Searches for latest ExchangeRate data by ID
     */
    ExchangeRate findFirstByCurrency_IdOrderByDateDesc(String id);
}

