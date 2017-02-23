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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortalApplication.class)
@Repository
public class TenantRepositoryTest {
  @Autowired
  private TenantRepository tenantRepository;

  private Tenant tenant;

  @Before
  public void execBefore() {
    User user = new User("test", "test");
    Group group = new Group("hoge1", "財務部", Arrays.asList(user));
    tenant = new Tenant("hoge1", "hoge1", Arrays.asList(group));
    group.setTenant(tenant);
    user.setGroups(Arrays.asList(group));
  }

  @Test
  public void saveAndDeleteInCamunda() throws IOException {
    int count = tenantRepository.findAllInCamunda(null).size();

    // Create
    assertTrue(tenantRepository.saveInCamunda(tenant));
    assertEquals(count + 1, tenantRepository.findAllInCamunda(null).size());

    // Delete
    assertTrue(tenantRepository.deleteFromCamunda(tenant.getId()));
    assertEquals(count, tenantRepository.findAllInCamunda(null).size());
  }

  @Test
  public void saveAndDeleteInDb() throws IOException {
    int count = tenantRepository.findAll().size();

    // Create
    assertNotNull(tenantRepository.save(tenant));
    assertEquals(count + 1, tenantRepository.findAll().size());

    tenant.getGroups().get(0).getUsers().get(0).setUsername("hogehoge");
    assertNotNull(tenantRepository.save(tenant));
    assertEquals(count + 1, tenantRepository.findAll().size());

    // Delete
    tenantRepository.delete(tenant.getId());
    assertEquals(count, tenantRepository.findAll().size());
  }

  @Test
  public void findAllByUserInCamunda() throws IOException {
    int count = tenantRepository.findAllInCamunda(new User("1", null)).size();
    assertEquals(1, count);
  }
}
