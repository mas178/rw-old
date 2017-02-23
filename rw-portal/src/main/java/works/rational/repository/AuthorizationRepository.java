package works.rational.repository;

import works.rational.domain.Authorization;

import java.io.IOException;
import java.util.List;

public interface AuthorizationRepository {
  Authorization saveInCamunda(final Authorization authorization) throws IOException;

  Boolean deleteFromCamunda(final String id);

  List<Authorization> findAllInCamunda(final Authorization condition) throws IOException;
}
