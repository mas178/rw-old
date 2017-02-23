package works.rational.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.PortalApplication;
import works.rational.domain.Authorization;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortalApplication.class)
@Repository
public class AuthorizationRepositoryTest {
  @Autowired
  private AuthorizationRepository repository;

  @Test
  public void normal() throws IOException {
    Authorization authorization = new Authorization();
    authorization.setGroupId(null);
    authorization.setUserId("4");
    authorization.setPermissions(Arrays.asList("READ", "READ_TASK"));
    authorization.setType(1);
    authorization.setResourceType(6);
    authorization.setResourceId("Process_06yj4to");

    Authorization condition = new Authorization(){{
      setResourceType(RESOURCE_TYPE.PROCESS_DEFINITION);
    }};

    int count = repository.findAllInCamunda(condition).size();

    // Create
    Authorization result = repository.saveInCamunda(authorization);
    assertNull(result.getGroupId());
    assertEquals(authorization.getUserId(), result.getUserId());
    assertEquals(authorization.getPermissions(), result.getPermissions());
    assertEquals(authorization.getType(), result.getType());
    assertEquals(authorization.getResourceType(), result.getResourceType());
    assertEquals(authorization.getResourceId(), result.getResourceId());
    assertEquals(count + 1, repository.findAllInCamunda(condition).size());

    // Delete
    repository.deleteFromCamunda(result.getId());
    assertEquals(count, repository.findAllInCamunda(condition).size());
  }
}
