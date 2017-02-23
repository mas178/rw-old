package works.rational.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.PortalApplication;
import works.rational.domain.Group;
import works.rational.domain.User;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortalApplication.class)
@Repository
public class GroupRepositoryTest {
  @Autowired
  private GroupRepository groupRepository;

  private Group group;

  @Before
  public void execBefore() {
    group = new Group("asdfghjk", "test", null);
  }

  @Test
  public void camundaApi() throws IOException {
    int count = groupRepository.findAllInCamunda(null).size();

    // Create
    assertTrue(groupRepository.saveInCamunda(group));
    assertEquals(count + 1, groupRepository.findAllInCamunda(null).size());

    // Delete
    assertTrue(groupRepository.deleteFromCamunda(group.getId()));
    assertEquals(count, groupRepository.findAllInCamunda(null).size());
  }

  @Test
  public void findAllByUserInCamunda() throws IOException {
    int count = groupRepository.findAllInCamunda(new User("1", null)).size();
    assertEquals(1, count);
  }
}
