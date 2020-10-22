package din.springframework.testtask.services;

import din.springframework.testtask.model.User;
import din.springframework.testtask.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends UserRepository {

}
