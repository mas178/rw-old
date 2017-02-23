package works.rational.repository.impl;

import org.springframework.stereotype.Repository;
import works.rational.domain.ProcessDefinition;
import works.rational.repository.CamundaApiRepository;
import works.rational.repository.ProcessDefinitionRepository;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProcessDefinitionRepositoryImpl extends CamundaApiRepository implements ProcessDefinitionRepository {

  public ProcessDefinitionRepositoryImpl() {
    resource = "process-definition";
  }

  @Override
  public List<ProcessDefinition> findAll() throws IOException {
    Response response = get(new HashMap<String, String>() {{
      put("latestVersion", "true");
    }});
    return Arrays.asList(responseToObject(response, ProcessDefinition[].class));
  }

  @Override
  public ProcessDefinition findOne(String id) throws IOException {
    return responseToObject(get(id), ProcessDefinition.class);
  }
}
