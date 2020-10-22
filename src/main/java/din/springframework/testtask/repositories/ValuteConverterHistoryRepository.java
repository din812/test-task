package din.springframework.testtask.repositories;

import din.springframework.testtask.model.ValuteConverterHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ValuteConverterHistoryRepository extends PagingAndSortingRepository<ValuteConverterHistory, UUID> {

    /**
     * Get collection of queries between two dates (dates themself are included)
     * @param queryDate LocalDate from
     * @param queryDate2 LocalDate to
     * @return Set<ValuteConverterHistory>
     */
    Set<ValuteConverterHistory> findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDate queryDate,
                                                                                                LocalDate queryDate2);

    Set<ValuteConverterHistory> findAllByUser_Id(Long id);

    Long countAllByUserId(Long userId);

    Page<ValuteConverterHistory> findAllByUserIdOrderByUuid(Long id, Pageable pageable);
}
