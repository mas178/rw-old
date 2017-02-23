package works.rational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import works.rational.domain.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, String>, TenantRepositoryCustom {
}
