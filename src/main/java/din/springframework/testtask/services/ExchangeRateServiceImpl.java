package din.springframework.testtask.services;

import din.springframework.testtask.model.ExchangeRate;
import din.springframework.testtask.repositories.ExchangeRateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("ALL")
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public boolean existsByDate(LocalDate date) {
        return exchangeRateRepository.existsByDate(date);
    }

    @Override
    public void deleteAllByDate(LocalDate date) {
        exchangeRateRepository.deleteAllByDate(date);
    }

    @Override
    public ExchangeRate findFirstByCurrency_IdOrderByDateDesc(String id) {
        return exchangeRateRepository.findFirstByCurrency_IdOrderByDateDesc(id);
    }


    @Override
    public <S extends ExchangeRate> S save(S entity) {
        return exchangeRateRepository.save(entity);
    }

    @Override
    public <S extends ExchangeRate> Iterable<S> saveAll(Iterable<S> entities) {
        return exchangeRateRepository.saveAll(entities);
    }

    @Override
    public Optional<ExchangeRate> findById(UUID uuid) {
        return exchangeRateRepository.findById(uuid);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return exchangeRateRepository.existsById(uuid);
    }

    @Override
    public Iterable<ExchangeRate> findAll() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public Iterable<ExchangeRate> findAllById(Iterable<UUID> uuids) {
        return exchangeRateRepository.findAllById(uuids);
    }

    @Override
    public long count() {
        return exchangeRateRepository.count();
    }

    @Override
    public void deleteById(UUID uuid) {
        exchangeRateRepository.deleteById(uuid);
    }


    @Override
    public void delete(ExchangeRate entity) {
        exchangeRateRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends ExchangeRate> entities) {
        exchangeRateRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        exchangeRateRepository.deleteAll();
    }
}
