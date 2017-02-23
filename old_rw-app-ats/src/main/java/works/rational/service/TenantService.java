package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Tenant;
import works.rational.repository.TenantRepository;

import java.util.List;

@Service
@Transactional
public class TenantService {
  private final TenantRepository tenantRepository;

  public TenantService(TenantRepository tenantRepository) {
    Assert.notNull(tenantRepository);
    this.tenantRepository = tenantRepository;
  }

  public List<Tenant> findAll() {
    return tenantRepository.findAll();
  }

  public Tenant findOne(String id) {
    return tenantRepository.findOne(id);
  }

  public Tenant save(Tenant tenant, UserDetails userDetails) {
    tenant.setCreatedUpdatedBy(userDetails.getUsername());
    return tenantRepository.save(tenant);
  }
}
