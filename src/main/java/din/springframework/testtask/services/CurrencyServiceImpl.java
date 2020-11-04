package din.springframework.testtask.services;

import din.springframework.testtask.model.Currency;
import din.springframework.testtask.repositories.CurrencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SuppressWarnings("ALL")
@Transactional
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }


    @Override
    public <S extends Currency> S save(S entity) {
        return currencyRepository.save(entity);
    }

    @Override
    public <S extends Currency> Iterable<S> saveAll(Iterable<S> entities) {
        return currencyRepository.saveAll(entities);
    }

    @Override
    public Optional<Currency> findById(String s) {
        return currencyRepository.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return currencyRepository.existsById(s);
    }

    @Override
    public Iterable<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public Iterable<Currency> findAllById(Iterable<String> strings) {
        return currencyRepository.findAllById(strings);
    }

    @Override
    public long count() {
        return currencyRepository.count();
    }

    @Override
    public void deleteById(String s) {
        currencyRepository.deleteById(s);
    }

    @Override
    public void delete(Currency entity) {
        currencyRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Currency> entities) {
        currencyRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        currencyRepository.deleteAll();
    }

    @Override
    public Iterable<Currency> findAll(Sort sort) {
        return currencyRepository.findAll(sort);
    }

    @Override
    public Page<Currency> findAll(Pageable pageable) {
        return currencyRepository.findAll(pageable);
    }
}
