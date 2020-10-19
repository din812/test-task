package din.springframework.testtask.repositories;

import din.springframework.testtask.model.CursValute;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CursValuteRepository extends CrudRepository<CursValute, UUID> {
    boolean existsByDate(String date);

    void deleteAllByDate(String date);

    Optional<CursValute> getCursValuteByDateAndValute_Id(String date, String id);
}
