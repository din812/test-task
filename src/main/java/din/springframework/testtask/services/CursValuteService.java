package din.springframework.testtask.services;

import din.springframework.testtask.model.CursValute;
import din.springframework.testtask.repositories.CursValuteRepository;

import java.util.Optional;
import java.util.UUID;

public interface CursValuteService extends CrudService<CursValute, UUID> {

    boolean existsByDate(String date);

    void deleteAllByDate(String date);

    Optional<CursValute> getCursValuteByDateAndId(String date, String id);

}
