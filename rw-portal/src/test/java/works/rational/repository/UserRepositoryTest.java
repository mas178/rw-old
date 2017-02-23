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
import works.rational.domain.Tenant;
import works.rational.domain.User;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortalApplication.class)
@Repository
public class UserRepositoryTest {
  @Autowired
  private UserRepository repository;

  private User user;

  @Before
  public void execBefore() {
    user = new User("test", "test");
  }

  @Test
  public void findOne() throws IOException {
    user = repository.findOneInCamunda("1");
    assertNotNull(user);
    // ToDo: 取ってこれるようにする。
    assertNull(user.getUsername());
  }

  @Test
  public void camundaApi() throws IOException {
    int count = repository.findAllInCamunda().size();

    // Create
    repository.saveInCamunda(user);
    assertEquals(count + 1, repository.findAllInCamunda().size());

    // Delete
    repository.deleteFromCamunda("test");
    assertEquals(count, repository.findAllInCamunda().size());
  }
}
