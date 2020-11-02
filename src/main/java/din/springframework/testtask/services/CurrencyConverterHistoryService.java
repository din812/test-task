package din.springframework.testtask.services;

import din.springframework.testtask.model.CurrencyConverterHistory;
import din.springframework.testtask.repositories.CurrencyConverterHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyConverterHistoryService extends CurrencyConverterHistoryRepository {
    Page<CurrencyConverterHistory> findHistoryId(Pageable pageable, Long id);
}
