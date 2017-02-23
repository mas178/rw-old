package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import works.rational.domain.Application;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String>, JpaSpecificationExecutor<Application> {
  @Query("SELECT x FROM Application x WHERE x.applicant.id IN :applicantIds AND x.company.id IN :companyIds ORDER BY x.updatedAt DESC")
  List<Application> findAllByApplicantCompanyIds(@Param("applicantIds") List<String> applicantIds, @Param("companyIds") List<String> companyIds);
}
