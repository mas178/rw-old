package works.rational.repository;

import works.rational.domain.Group;
import works.rational.domain.User;

import java.io.IOException;
import java.util.List;

public interface GroupRepositoryCustom {
  List<Group> findAllInCamunda(User user) throws IOException;

  Boolean saveInCamunda(Group group);

  Boolean deleteFromCamunda(String id);
}
