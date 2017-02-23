package works.rational.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import works.rational.domain.Task;

import java.sql.Timestamp;

public interface TaskRepository extends JpaRepository<Task, String>, JpaSpecificationExecutor<Task> {
  class TaskSpecifications {
    public static Specification<Task> overDue() {
      return (root, query, cb) -> cb.greaterThan(root.get("plannedTimestamp"), new Timestamp(System.currentTimeMillis()));
    }
  }
}
