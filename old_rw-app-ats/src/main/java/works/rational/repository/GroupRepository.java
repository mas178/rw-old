package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import works.rational.domain.Group;

public interface GroupRepository extends JpaRepository<Group, String>, JpaSpecificationExecutor<Group> {
}
