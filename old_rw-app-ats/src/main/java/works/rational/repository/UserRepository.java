package works.rational.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import works.rational.domain.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
  @Query("SELECT x FROM User x ORDER BY x.username")
  Page<User> findAllOrderById(Pageable pageable);

  @Query("SELECT x FROM User x WHERE x.username = :username")
  User findOne(@Param("username") String username);
}
