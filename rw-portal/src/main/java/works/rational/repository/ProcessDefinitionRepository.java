package works.rational.repository;

import works.rational.domain.ProcessDefinition;

import java.io.IOException;
import java.util.List;

public interface ProcessDefinitionRepository {
  List<ProcessDefinition> findAll() throws IOException;

  ProcessDefinition findOne(String id) throws IOException;
}
