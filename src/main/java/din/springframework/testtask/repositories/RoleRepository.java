package din.springframework.testtask.repositories;

import din.springframework.testtask.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
}
