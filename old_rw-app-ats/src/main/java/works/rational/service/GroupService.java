package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Group;
import works.rational.repository.GroupRepository;

import java.util.List;

@Service
@Transactional
public class GroupService {
  private final GroupRepository groupRepository;

  public GroupService(GroupRepository groupRepository) {
    Assert.notNull(groupRepository);
    this.groupRepository = groupRepository;
  }

  public List<Group> findAll() {
    return groupRepository.findAll();
  }

  public Group findOne(String id) {
    return groupRepository.findOne(id);
  }

  public Group save(Group group, UserDetails userDetails) {
    group.setCreatedUpdatedBy(userDetails.getUsername());
    return groupRepository.save(group);
  }
}
