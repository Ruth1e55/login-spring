package regis.login.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import regis.login.domain.Role;

public interface RoleRepository  extends MongoRepository<Role, String> {

    Role findByRole(String role);
}
