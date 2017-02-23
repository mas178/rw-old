package works.rational.repository;

import works.rational.domain.Tenant;
import works.rational.domain.User;

import java.io.IOException;
import java.util.List;

public interface TenantRepositoryCustom {
  List<Tenant> findAllInCamunda(User user) throws IOException;

  Boolean saveInCamunda(Tenant tenant);

  Boolean deleteFromCamunda(String id);
}
