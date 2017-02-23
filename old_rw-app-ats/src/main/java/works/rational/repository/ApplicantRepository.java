package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import works.rational.domain.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, String>, JpaSpecificationExecutor<Applicant> {
}
