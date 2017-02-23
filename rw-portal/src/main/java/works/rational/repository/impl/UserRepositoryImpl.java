package works.rational.repository.impl;

import org.springframework.stereotype.Repository;
import works.rational.domain.User;
import works.rational.repository.CamundaApiRepository;
import works.rational.repository.UserRepositoryCustom;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;

@Repository
public class UserRepositoryImpl extends CamundaApiRepository implements UserRepositoryCustom {

  public UserRepositoryImpl() {
    resource = "user";
  }

  @Override
  public Boolean saveInCamunda(User user) throws IOException {
    return post(user).getStatus() == HTTP_OK;
  }

  @Override
  public List<User> findAllInCamunda() throws IOException {
    return Arrays.asList(responseToObject(get(), User[].class));
  }

  @Override
  public User findOneInCamunda(String id) throws IOException {
    return responseToObject(get(id + "/profile"), User.class);
  }

  @Override
  public Boolean deleteFromCamunda(String id) throws IOException {
    return delete(id).getStatus() == HTTP_NO_CONTENT;
  }
}
