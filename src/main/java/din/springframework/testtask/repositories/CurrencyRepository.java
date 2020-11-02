package din.springframework.testtask.repositories;

import din.springframework.testtask.model.Currency;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CurrencyRepository extends PagingAndSortingRepository<Currency, String> {
}
