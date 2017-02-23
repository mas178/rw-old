package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = {"applicants", "applications", "companies", "jobs"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task extends Common {
  @Column(nullable = false, length = 127)
  String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  Status status;

  @Column
  Timestamp plannedTimestamp;

  @Column
  Timestamp actualTimestamp;

  @Column
  String plannedAction;

  @Column
  String actualAction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "in_charge_user_username", nullable = false)
  User user;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "tasks_applicants",
          joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "applicant_id", referencedColumnName = "id"))
  List<Applicant> applicants;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "tasks_applications",
          joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "application_id", referencedColumnName = "id"))
  List<Application> applications;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "tasks_companies",
          joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"))
  List<Company> companies;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "tasks_jobs",
          joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"))
  List<Job> jobs;
}
