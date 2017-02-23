package works.rational.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import works.rational.domain.ProcessDefinition;
import works.rational.repository.ProcessDefinitionRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ProcessDefinitionService {
  private final ProcessDefinitionRepository processDefinitionRepository;

  public ProcessDefinitionService(ProcessDefinitionRepository processDefinitionRepository) {
    Assert.notNull(processDefinitionRepository);
    this.processDefinitionRepository = processDefinitionRepository;
  }

  public List<ProcessDefinition> findAll() throws IOException {
    return processDefinitionRepository.findAll();
  }

  public ProcessDefinition findOne(String id) throws IOException {
    return processDefinitionRepository.findOne(id);
  }
}
