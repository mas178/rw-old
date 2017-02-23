package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import works.rational.domain.Job;

public interface JobRepository extends JpaRepository<Job, String>, JpaSpecificationExecutor<Job> {
}
