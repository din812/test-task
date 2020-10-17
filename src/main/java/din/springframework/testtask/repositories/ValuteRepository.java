package din.springframework.testtask.repositories;

import din.springframework.testtask.model.Valute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ValuteRepository extends CrudRepository<Valute, String> {
    Set<Valute> findValutesByValcurs_Date(String date);
}
