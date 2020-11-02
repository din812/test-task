package din.springframework.testtask.services;

import din.springframework.testtask.model.CurrencyConverterHistory;
import din.springframework.testtask.repositories.UserRepository;
import din.springframework.testtask.repositories.CurrencyConverterHistoryRepository;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CurrencyConverterHistoryServiceImpl implements CurrencyConverterHistoryService {

    private final CurrencyConverterHistoryRepository currencyConverterHistoryRepository;
    private final UserRepository userRepository;

    public CurrencyConverterHistoryServiceImpl(CurrencyConverterHistoryRepository currencyConverterHistoryRepository,
                                                                                        UserRepository userRepository) {
        this.currencyConverterHistoryRepository = currencyConverterHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Set<CurrencyConverterHistory>
                findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDate queryDate, LocalDate queryDate2) {
        return currencyConverterHistoryRepository.findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(queryDate,
                queryDate2);
    }

    @Override
    public Set<CurrencyConverterHistory> findAllByUser_Id(Long id) {
        Set<CurrencyConverterHistory> historySet = currencyConverterHistoryRepository.findAllByUser_Id(id);
        historySet
                .removeIf(currency -> currency.getUser() != userRepository.findById(id)
                        .orElse(null));
        return historySet;
    }

    @Override
    public Page<CurrencyConverterHistory> findHistoryId(Pageable pageable, Long id) {
        return new PageImpl<>(Lists.newArrayList(currencyConverterHistoryRepository.findAllByUser_Id(id)));
    }

    @Override
    public Long countAllByUserId(Long userId) {
        return currencyConverterHistoryRepository.countAllByUserId(userId);
    }

    @Override
    public Page<CurrencyConverterHistory> findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(Long id,
                                                                                                    Pageable pageable) {
        return currencyConverterHistoryRepository.findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(id,
                                                                                                              pageable);
    }

    @Override
    public Iterable<CurrencyConverterHistory> findAll(Sort sort) {
        return currencyConverterHistoryRepository.findAll(sort);
    }

    @Override
    public Page<CurrencyConverterHistory> findAll(Pageable pageable) {
        return currencyConverterHistoryRepository.findAll(pageable);
    }

    @Override
    public <S extends CurrencyConverterHistory> S save(S entity) {
        return currencyConverterHistoryRepository.save(entity);
    }

    @Override
    public <S extends CurrencyConverterHistory> Iterable<S> saveAll(Iterable<S> entities) {
        return currencyConverterHistoryRepository.saveAll(entities);
    }

    @Override
    public Optional<CurrencyConverterHistory> findById(UUID uuid) {
        return currencyConverterHistoryRepository.findById(uuid);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return currencyConverterHistoryRepository.existsById(uuid);
    }

    @Override
    public Iterable<CurrencyConverterHistory> findAll() {
        return currencyConverterHistoryRepository.findAll();
    }

    @Override
    public Iterable<CurrencyConverterHistory> findAllById(Iterable<UUID> uuids) {
        return currencyConverterHistoryRepository.findAllById(uuids);
    }

    @Override
    public long count() {
        return currencyConverterHistoryRepository.count();
    }

    @Override
    public void deleteById(UUID uuid) {
        currencyConverterHistoryRepository.deleteById(uuid);
    }

    @Override
    public void delete(CurrencyConverterHistory entity) {
        currencyConverterHistoryRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends CurrencyConverterHistory> entities) {
        currencyConverterHistoryRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        currencyConverterHistoryRepository.deleteAll();
    }
}
