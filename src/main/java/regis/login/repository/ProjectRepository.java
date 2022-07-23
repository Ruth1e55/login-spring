package regis.login.repository;

import org.springframework.data.repository.CrudRepository;
import regis.login.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, String> {

    @Override
    void delete(Project project);

}
