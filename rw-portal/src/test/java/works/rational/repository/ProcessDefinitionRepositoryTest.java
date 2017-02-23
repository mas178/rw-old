package works.rational.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.PortalApplication;
import works.rational.domain.ProcessDefinition;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortalApplication.class)
@Repository
public class ProcessDefinitionRepositoryTest {
  @Autowired
  private ProcessDefinitionRepository repository;

  @Test
  public void normal() throws IOException {
    List<ProcessDefinition> processDefinitions = repository.findAll();
    assertEquals(3, processDefinitions.size());
  }

  @Test
  public void findOne() throws IOException {
    String sampleId = repository.findAll().get(0).getId();
    System.out.println("[ProcessDefinitionRepositoryTest#findOne] " + sampleId);
    ProcessDefinition processDefinition = repository.findOne(sampleId);
    assertNotNull(processDefinition);
    assertNotNull(processDefinition.getId());
  }
}
