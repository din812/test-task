package din.springframework.testtask.services;

import din.springframework.testtask.model.ValuteConverterHistory;
import din.springframework.testtask.repositories.ValuteConverterHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

public interface ValuteConverterHistoryService extends ValuteConverterHistoryRepository {
    Page<ValuteConverterHistory> findHistoryId(Pageable pageable, Long id);
}
