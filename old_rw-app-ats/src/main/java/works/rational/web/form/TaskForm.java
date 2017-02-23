package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import works.rational.domain.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class TaskForm extends Common {
  @NotNull
  @Size(min = 1, max = 127)
  String name;
  @NotNull
  String statusId;
  String plannedTimestamp;
  String actualTimestamp;
  String plannedAction;
  String actualAction;
  @NotNull
  String username;
  List<String> applicantIds;
  List<String> applicationIds;
  List<String> companyIds;
  List<String> jobIds;

  public void setValues(Task task) {
    BeanUtils.copyProperties(task, this);
    this.setUsername(task.getUser().getUsername());
    this.setStatusId(task.getStatus().getId());
    this.setPlannedTimestamp(Task.Util.timestampToStr(task.getPlannedTimestamp()));
    this.setActualTimestamp(Task.Util.timestampToStr(task.getActualTimestamp()));
    this.setApplicantIds(task.getApplicants().stream().map(Applicant::getId).collect(Collectors.toList()));
    this.setApplicationIds(task.getApplications().stream().map(Application::getId).collect(Collectors.toList()));
    this.setCompanyIds(task.getCompanies().stream().map(Company::getId).collect(Collectors.toList()));
    this.setJobIds(task.getJobs().stream().map(Job::getId).collect(Collectors.toList()));
  }
}
