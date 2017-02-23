package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import works.rational.domain.User;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
}
