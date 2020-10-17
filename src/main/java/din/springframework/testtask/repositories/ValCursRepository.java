package din.springframework.testtask.repositories;

import din.springframework.testtask.model.ValCurs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ValCursRepository extends CrudRepository<ValCurs, String> {

    ValCurs getValCursByDateEquals(String date);
}
