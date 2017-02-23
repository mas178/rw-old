package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import works.rational.domain.Group;

public interface GroupRepository extends JpaRepository<Group, String>, GroupRepositoryCustom {
}
