package din.springframework.testtask.repositories;

import din.springframework.testtask.model.ValuteConverterHistory;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ValuteConverterHistoryRepository extends CrudRepository<ValuteConverterHistory, UUID> {

    /**
     * Get collection of queries between two dates (dates themself are included)
     * @param queryDate LocalDate from
     * @param queryDate2 LocalDate to
     * @return Set<ValuteConverterHistory>
     */
    Set<ValuteConverterHistory> findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDate queryDate,
                                                                                                LocalDate queryDate2);
}
