package works.rational.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.Main;
import works.rational.domain.Tenant;
import works.rational.domain.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@Service
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @Autowired
  private TenantService tenantService;

  private User user;

  private UserDetails userDetails;

  @Before
  public void before() {
    user = new User();
    user.setUsername("testUserName");
    user.setEncodedPassword("password");
    user.setTimeStamp("testUserName");
    user.setDescription("test user description");

    userDetails = new LoginUserDetails(user);

    Tenant tenant = new Tenant();
    tenant.setName("test tenant");
    tenant.setCreatedUpdatedBy("test user");
    tenantService.save(tenant, userDetails);

    user.setTenant(tenant);
  }

  @Test
  public void normal() {
    List<User> users = userService.findAll();
    int count = users.size();

    assertNotNull(userService.save(user, userDetails));
    assertEquals(count + 1, userService.findAll().size());
  }
}
