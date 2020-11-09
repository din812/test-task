package din.springframework.testtask.repositories;

import din.springframework.testtask.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    /**
     * Searches database for required username and returns User entity if DB contains user with such username else null
     */
    User findByUsername(String username);
}
