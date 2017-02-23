package works.rational.repository.impl;

import org.springframework.stereotype.Repository;
import works.rational.domain.Tenant;
import works.rational.domain.User;
import works.rational.repository.CamundaApiRepository;
import works.rational.repository.TenantRepositoryCustom;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

@Repository
public class TenantRepositoryImpl extends CamundaApiRepository implements TenantRepositoryCustom {

  public TenantRepositoryImpl() {
    resource = "tenant";
  }

  @Override
  public List<Tenant> findAllInCamunda(User user) throws IOException {
    Response response = get(new HashMap<String, String>() {{
      if (user != null) {
        if (!user.getUsername().isEmpty()) {
          put("userMember", user.getUsername());
        }
      }
    }});
    return Arrays.asList(responseToObject(response, Tenant[].class));
  }

  @Override
  public Boolean saveInCamunda(Tenant tenant) {
    return post(tenant).getStatus() == HTTP_NO_CONTENT;
  }

  @Override
  public Boolean deleteFromCamunda(String id) {
    return super.delete(id).getStatus() == HTTP_NO_CONTENT;
  }
}
