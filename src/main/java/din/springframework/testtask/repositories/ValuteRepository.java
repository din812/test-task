package din.springframework.testtask.repositories;

import din.springframework.testtask.model.Valute;
import org.springframework.data.repository.CrudRepository;

public interface ValuteRepository extends CrudRepository<Valute, String > {
}
