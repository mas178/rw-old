package works.rational.service;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import works.rational.domain.Authorization;
import works.rational.domain.Group;
import works.rational.domain.ProcessDefinition;
import works.rational.domain.User;
import works.rational.repository.AuthorizationRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthorizationService {
  private final AuthorizationRepository authorizationRepository;

  public AuthorizationService(AuthorizationRepository authorizationRepository) {
    Assert.notNull(authorizationRepository);
    this.authorizationRepository = authorizationRepository;
  }

  /**
   * Grant process definition authorization to group
   *
   * @param processDefinition Grant authorization of process definition
   * @param group             Grant authorization to group
   * @return authorizations granted
   */
  private Authorization grant(ProcessDefinition processDefinition, Group group) throws IOException {
    // 一回全権限を剥奪してから付与する
    // ToDo: これでいいか？付与する権限より強い権限を元から付与されている場合がありえるなら、権限が減ってしまう。
    revoke(processDefinition, group);

    return authorizationRepository.saveInCamunda(new Authorization() {{
      setType(TYPE.GRANT);
      setPermissions(PERMISSION.PROCESS_DEFINITION);
      setGroupId(group.getId());
      setResourceType(RESOURCE_TYPE.PROCESS_DEFINITION);
      setResourceId(processDefinition.getId());
    }});
  }

  /**
   * Grant process definition authorization to user
   *
   * @param processDefinition Grant authorization of process definition
   * @param user              Grant authorization to user
   * @return authorizations granted
   */
  public Stream<Authorization> grant(ProcessDefinition processDefinition, User user) throws IOException {
    return user.getGroups().stream().map(group -> {
      try {
        return grant(processDefinition, group);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    });
  }

  /**
   * Revoke process definition authorization from group
   *
   * @param processDefinition revoke authorization of process definition
   * @param group             revoke authorization from group
   * @return fail count
   */
  private int revoke(ProcessDefinition processDefinition, Group group) throws IOException {
    List<Authorization> authorizations = authorizationRepository.findAllInCamunda(new Authorization() {{
      setType(TYPE.GRANT);
      setGroupId(group.getId());
      setResourceType(RESOURCE_TYPE.PROCESS_DEFINITION);
      setResourceId(processDefinition.getId());
    }});

    Stream<Boolean> results = authorizations.stream().map(authorization -> authorizationRepository.deleteFromCamunda(authorization.getId()));

    return results.collect(Collectors.summingInt((result -> BooleanUtils.toInteger(result, 0, 1))));
  }

  /**
   * Revoke process definition authorization from user
   *
   * @param processDefinition revoke authorization of process definition
   * @param user              revoke authorization from user
   * @return fail count
   */
  public int revoke(ProcessDefinition processDefinition, User user) throws IOException {
    return user.getGroups().stream().map(group -> {
      try {
        return revoke(processDefinition, group);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }).collect(Collectors.summingInt(x -> x)); // ToDo: 何か変。もっと良い書き方があるはず。
  }
}
