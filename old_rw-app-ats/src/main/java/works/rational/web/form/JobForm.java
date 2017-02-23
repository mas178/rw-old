package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import works.rational.domain.Application;
import works.rational.domain.Job;
import works.rational.domain.Task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class JobForm extends Common {
  @NotNull
  @Size(min = 1, max = 127)
  String name;
  @NotNull
  String statusId;
  @NotNull
  String companyId;
  List<Application> applications;
  List<Task> tasks;

  public void setJob(Job job) {
    BeanUtils.copyProperties(job, this);
    this.setCompanyId(job.getCompany().getId());
    this.setStatusId(job.getStatus().getId());
  }
}
