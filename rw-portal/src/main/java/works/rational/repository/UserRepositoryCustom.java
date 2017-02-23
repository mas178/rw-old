package works.rational.repository;

import works.rational.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserRepositoryCustom {
  Boolean saveInCamunda(User user) throws IOException;

  List<User> findAllInCamunda() throws IOException;

  User findOneInCamunda(String id) throws IOException;

  Boolean deleteFromCamunda(String id) throws IOException;
}
