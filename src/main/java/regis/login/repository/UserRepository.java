package regis.login.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import regis.login.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);


}
