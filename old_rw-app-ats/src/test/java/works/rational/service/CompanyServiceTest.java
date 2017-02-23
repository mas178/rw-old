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
import works.rational.domain.Company;
import works.rational.domain.Status;
import works.rational.domain.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@Service
public class CompanyServiceTest {
  @Autowired
  private CompanyService companyService;

  @Autowired
  private StatusService statusService;

  private Company company;

  private UserDetails userDetails;

  @Before
  public void before() {
    User user = new User();
    user.setUsername("testUserName");
    user.setEncodedPassword("password");
    user.setTimeStamp("testUserName");
    user.setDescription("test user description");

    userDetails = new LoginUserDetails(user);

    Status status = new Status();
    status.setName("test");
    status.setCreatedUpdatedBy("hoge");
    status.setDomain("company");
    statusService.save(status, userDetails);

    company = new Company();
    company.setName("Test");
    company.setStatus(status);
    company.setDescription("TestTestTestTestTestTest");
    company.setCreatedUpdatedBy("testUser");
  }

  @Test
  public void normal() {
    List<Company> companies = companyService.findAll();
    int count = companies.size();

    assertNotNull(companyService.save(this.company, userDetails));
    assertEquals(count + 1, companyService.findAll().size());
  }
}
