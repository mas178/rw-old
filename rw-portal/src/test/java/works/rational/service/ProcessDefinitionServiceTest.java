package works.rational.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.PortalApplication;
import works.rational.domain.ProcessDefinition;
import works.rational.repository.UserRepository;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortalApplication.class)
public class ProcessDefinitionServiceTest {
  @Autowired
  private ProcessDefinitionService service;

  @Test
  public void findAll() throws IOException {
    List<ProcessDefinition> processDefinitions = service.findAll();
    assertEquals(3, processDefinitions.size());
  }
}
