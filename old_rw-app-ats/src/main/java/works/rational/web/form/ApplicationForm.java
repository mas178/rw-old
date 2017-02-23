package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import works.rational.domain.Application;
import works.rational.domain.Job;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class ApplicationForm extends Common {
  @NotNull
  String applicantId;

  @NotNull
  String statusId;

  @NotNull
  String companyId;

  @NotNull
  List<String> jobIds;

  List<String> tasks;

  public void setValues(Application application) {
    BeanUtils.copyProperties(application, this);
    this.setApplicantId(application.getApplicant().getId());
    this.setStatusId(application.getStatus().getId());
    this.setCompanyId(application.getCompany().getId());
    this.setJobIds(application.getJobs().stream().map(Job::getId).collect(Collectors.toList()));
  }
}
