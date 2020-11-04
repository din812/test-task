package din.springframework.testtask.repositories;

import din.springframework.testtask.model.CurrencyConverterHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface CurrencyConverterHistoryRepository extends PagingAndSortingRepository<CurrencyConverterHistory, UUID> {

    /**
     * Get collection of queries between two dates (dates themself are included)
     * @param queryDate LocalDate from
     * @param queryDate2 LocalDate to
     * @return Set<CurrencyConverterHistory>
     */
    Set<CurrencyConverterHistory> findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDateTime queryDate,
                                                                                              LocalDateTime queryDate2);

    Set<CurrencyConverterHistory> findAllByUser_Id(Long id);

    Long countAllByUserId(Long userId);

    /**
     * Get collection of queries by User ID and sort them by Query Date and it's ID.
     * @param id user ID
     * @param pageable needed for paging
     * @return pageable
     */
    Page<CurrencyConverterHistory> findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(Long id,
                                                                                                    Pageable pageable);
}
