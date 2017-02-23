package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import works.rational.domain.Group;
import works.rational.domain.Task;
import works.rational.domain.User;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class UserForm {
  String username;
  String encodedPassword;
  @Size(max = 1200)
  String description;
  String createdBy;
  String updatedBy;
  List<String> groupIds;
  List<String> taskIds;

  public void setValues(User user) {
    BeanUtils.copyProperties(user, this);
    this.setGroupIds(user.getGroups().stream().map(Group::getId).collect(Collectors.toList()));
    this.setTaskIds(user.getTasks().stream().map(Task::getId).collect(Collectors.toList()));
  }
}
