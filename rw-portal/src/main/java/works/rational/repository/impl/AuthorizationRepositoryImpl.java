package works.rational.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import works.rational.domain.Authorization;
import works.rational.repository.AuthorizationRepository;
import works.rational.repository.CamundaApiRepository;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

@Repository
public class AuthorizationRepositoryImpl extends CamundaApiRepository implements AuthorizationRepository {

  public AuthorizationRepositoryImpl() {
    resource = "authorization";
  }

  @Override
  public Authorization saveInCamunda(final Authorization authorization) throws IOException {
    return responseToObject(post(authorization), Authorization.class);
  }

  @Override
  public List<Authorization> findAllInCamunda(final Authorization condition) throws IOException {
    Response response = get(new HashMap<String, String>() {{
      if (condition != null) {
        if (condition.getType() != null) {
          put("type", condition.getType().toString());
        }
        if (StringUtils.isNotEmpty(condition.getUserId())) {
          put("userIdIn", condition.getUserId());
        }
        if (StringUtils.isNotEmpty(condition.getGroupId())) {
          put("groupIdIn", condition.getGroupId());
        }
        if (condition.getResourceType() != null) {
          put("resourceType", condition.getResourceType().toString());
        }
        if (StringUtils.isNotEmpty(condition.getResourceId())) {
          put("resourceId", condition.getResourceId());
        }
      }
    }});
    return Arrays.asList(responseToObject(response, Authorization[].class));
  }

  @Override
  public Boolean deleteFromCamunda(final String id) {
    return super.delete(id).getStatus() == HTTP_NO_CONTENT;
  }
}
