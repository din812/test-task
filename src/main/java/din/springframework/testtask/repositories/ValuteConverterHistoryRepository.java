package din.springframework.testtask.repositories;

import din.springframework.testtask.model.ValuteConverterHistory;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

public interface ValuteConverterHistoryRepository extends CrudRepository<ValuteConverterHistory, UUID> {

    Set<ValuteConverterHistory> getValuteConverterHistoriesByQueryDateIsBetween(Date queryDate, Date queryDate2);
}
