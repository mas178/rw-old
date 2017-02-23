package works.rational.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.PortalApplication;
import works.rational.domain.Authorization;
import works.rational.domain.Group;
import works.rational.domain.ProcessDefinition;
import works.rational.domain.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortalApplication.class)
@Service
public class AuthorizationServiceTest {
  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private ProcessDefinitionService processDefinitionService;

  @Test
  public void normal() throws IOException {
    User user = new User("AuthorizationServiceTest", "AuthorizationServiceTest");
    Group group1 = new Group("1", "hoge", Arrays.asList(user));
    Group group2 = new Group("2", "hoge", Arrays.asList(user));
    Group group3 = new Group("3", "hoge", Arrays.asList(user));
    user.setGroups(Arrays.asList(group1, group2, group3));

    ProcessDefinition processDefinition = processDefinitionService.findAll().get(0);
    Stream<Authorization> authorizations = authorizationService.grant(processDefinition, user);
    authorizations.forEach(System.out::println);
    assertNotNull(authorizations);

    assertEquals(0, authorizationService.revoke(processDefinition, user));
  }
}
