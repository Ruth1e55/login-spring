package regis.login.repository;

import org.springframework.data.repository.CrudRepository;
import regis.login.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, String> {

    @Override
    void delete(Company company);
}
