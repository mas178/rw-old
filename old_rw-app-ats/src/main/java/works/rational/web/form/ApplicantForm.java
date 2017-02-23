package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import works.rational.domain.Applicant;
import works.rational.domain.Application;
import works.rational.domain.Task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class ApplicantForm extends Common {
  @NotNull
  @Size(min = 1, max = 127)
  String name;
  @NotNull
  String statusId;
  Date birthDay;
  Integer age;
  List<Application> applications;
  List<Task> tasks;

  public void setValues(Applicant applicant) {
    BeanUtils.copyProperties(applicant, this);
    this.setStatusId(applicant.getStatus().getId());
  }
}
