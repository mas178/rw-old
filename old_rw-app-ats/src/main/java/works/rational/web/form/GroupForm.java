package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import works.rational.domain.Group;
import works.rational.domain.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class GroupForm extends Common {
  @NotNull
  @Size(min = 1, max = 127)
  String name;

  @NotNull
  String tenantId;

  List<String> usernames;

  public void setValues(Group group) {
    BeanUtils.copyProperties(group, this);
    this.setTenantId(group.getTenant().getId());
    this.setUsernames(group
            .getUsers()
            .stream()
            .map(User::getUsername)
            .collect(Collectors.toList()));
  }
}
