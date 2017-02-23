package works.rational.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import works.rational.domain.Group;
import works.rational.domain.User;
import works.rational.repository.CamundaApiRepository;
import works.rational.repository.GroupRepositoryCustom;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

@Repository
public class GroupRepositoryImpl extends CamundaApiRepository implements GroupRepositoryCustom {

  public GroupRepositoryImpl() {
    resource = "group";
  }

  @Override
  public List<Group> findAllInCamunda(User user) throws IOException {
    Response response = get(new HashMap<String, String>() {{
      if (user != null) {
        if (StringUtils.isNotEmpty(user.getUsername())) {
          put("member", user.getUsername());
        }
      }
    }});
    return Arrays.asList(responseToObject(response, Group[].class));
  }

  @Override
  public Boolean saveInCamunda(Group group) {
    return post(group).getStatus() == HTTP_NO_CONTENT;
  }

  @Override
  public Boolean deleteFromCamunda(String id) {
    return super.delete(id).getStatus() == HTTP_NO_CONTENT;
  }
}