package works.rational.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import works.rational.domain.CamundaDomain;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class CamundaApiRepository {
  private final WebTarget webTarget;

  protected String resource;

  protected CamundaApiRepository() {
    Client client = ClientBuilder.newClient();
    webTarget = client.target("http://localhost:8080").path("engine-rest");
  }

  protected Response get(final Map<String, String> params) {
    return addParams(webTarget.path(resource), params)
            .request(MediaType.APPLICATION_JSON).get();
  }

  protected Response get(final String id_path) {
    return webTarget.path(resource).path(id_path).request(MediaType.APPLICATION_JSON).get();
  }

  protected Response get() {
    return get(new HashMap<>());
  }

  protected Response post(final CamundaDomain domain) {
    Entity<String> entity = Entity.entity(domain.toJson(), MediaType.APPLICATION_JSON_TYPE);
    return webTarget.path(resource).path("create").request().post(entity);
  }

  protected Response delete(final String id) {
    return webTarget.path(resource).path(id).request().delete();
  }

  protected <T> T responseToObject(final Response response, final Class<T> valueType) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String json = response.readEntity(String.class);
    return mapper.readValue(json, valueType);
  }

  private WebTarget addParams(final WebTarget target, final Map<String, String> params) {
    WebTarget t = target;
    if (params != null) {
      for (Map.Entry<String, String> param : params.entrySet()) {
        t = t.queryParam(param.getKey(), param.getValue());
      }
    }
    return t;
  }
}
