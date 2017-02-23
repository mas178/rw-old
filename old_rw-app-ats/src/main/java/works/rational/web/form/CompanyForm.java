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
public class CompanyForm extends Common {
  @NotNull
  @Size(min = 1, max = 127)
  String name;
  @NotNull
  String statusId;
  @NotNull
  List<String> categoryIds;
  List<Job> jobs;
  List<Application> applications;
  List<Task> tasks;

  public void setValues(Company company) {
    BeanUtils.copyProperties(company, this);
    this.setCategoryIds(company.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
    this.setStatusId(company.getStatus().getId());
  }
}
