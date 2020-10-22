package din.springframework.testtask.services;

import din.springframework.testtask.model.ValuteConverterHistory;
import din.springframework.testtask.repositories.UserRepository;
import din.springframework.testtask.repositories.ValuteConverterHistoryRepository;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ValuteConverterHistoryServiceImpl implements ValuteConverterHistoryService {

    private final ValuteConverterHistoryRepository valuteConverterHistoryRepository;
    private final UserRepository userRepository;

    public ValuteConverterHistoryServiceImpl(ValuteConverterHistoryRepository valuteConverterHistoryRepository, UserRepository userRepository) {
        this.valuteConverterHistoryRepository = valuteConverterHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Set<ValuteConverterHistory> findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDate queryDate, LocalDate queryDate2) {
        return valuteConverterHistoryRepository.findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(queryDate,
                queryDate2);
    }

    @Override
    public Set<ValuteConverterHistory> findAllByUser_Id(Long id) {
        Set<ValuteConverterHistory> historySet = valuteConverterHistoryRepository.findAllByUser_Id(id);
        historySet.removeIf(valute -> valute.getUser() != userRepository.findById(id).orElse(null));
        return historySet;
    }

    @Override
    public Page<ValuteConverterHistory> findHistoryId(Pageable pageable, Long id) {
        Page<ValuteConverterHistory> histories =
                new PageImpl<ValuteConverterHistory>(Lists.newArrayList(valuteConverterHistoryRepository
                                                                                            .findAllByUser_Id(id)));
        return histories;

    }

    @Override
    public Long countAllByUserId(Long userId) {
        return valuteConverterHistoryRepository.countAllByUserId(userId);
    }

    @Override
    public Page<ValuteConverterHistory> findAllByUserIdOrderByUuid(Long id, Pageable pageable) {
        return valuteConverterHistoryRepository.findAllByUserIdOrderByUuid(id, pageable);
    }

    @Override
    public Iterable<ValuteConverterHistory> findAll(Sort sort) {
        return valuteConverterHistoryRepository.findAll(sort);
    }

    @Override
    public Page<ValuteConverterHistory> findAll(Pageable pageable) {
        return valuteConverterHistoryRepository.findAll(pageable);
    }

    @Override
    public <S extends ValuteConverterHistory> S save(S entity) {
        return valuteConverterHistoryRepository.save(entity);
    }

    @Override
    public <S extends ValuteConverterHistory> Iterable<S> saveAll(Iterable<S> entities) {
        return valuteConverterHistoryRepository.saveAll(entities);
    }

    @Override
    public Optional<ValuteConverterHistory> findById(UUID uuid) {
        return valuteConverterHistoryRepository.findById(uuid);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return valuteConverterHistoryRepository.existsById(uuid);
    }

    @Override
    public Iterable<ValuteConverterHistory> findAll() {
        return valuteConverterHistoryRepository.findAll();
    }

    @Override
    public Iterable<ValuteConverterHistory> findAllById(Iterable<UUID> uuids) {
        return valuteConverterHistoryRepository.findAllById(uuids);
    }

    @Override
    public long count() {
        return valuteConverterHistoryRepository.count();
    }

    @Override
    public void deleteById(UUID uuid) {
        valuteConverterHistoryRepository.deleteById(uuid);
    }

    @Override
    public void delete(ValuteConverterHistory entity) {
        valuteConverterHistoryRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends ValuteConverterHistory> entities) {
        valuteConverterHistoryRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        valuteConverterHistoryRepository.deleteAll();
    }
}
