package din.springframework.testtask.repositories;

import din.springframework.testtask.model.CurrencyConverterHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface CurrencyConverterHistoryRepository extends PagingAndSortingRepository<CurrencyConverterHistory, UUID> {

    /**
     * Get collection of queries between two dates (dates themself are included)
     * @param queryDate LocalDate from
     * @param queryDate2 LocalDate to
     * @return Set<CurrencyConverterHistory>
     */
    Set<CurrencyConverterHistory> findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDate queryDate,
                                                                                              LocalDate queryDate2);

    Set<CurrencyConverterHistory> findAllByUser_Id(Long id);

    Long countAllByUserId(Long userId);

    Page<CurrencyConverterHistory> findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(Long id,
                                                                                                    Pageable pageable);
}
