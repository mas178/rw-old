package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import works.rational.domain.Applicant;
import works.rational.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Applicant> {
}
