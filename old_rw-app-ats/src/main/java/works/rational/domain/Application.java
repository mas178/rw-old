package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = {"jobs", "tasks"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application extends Common {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "applicant_id", nullable = false)
  Applicant applicant; // ToDo: updatable = false かも

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", nullable = false)
  Company company; // ToDo: updatable = false かも

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "jobs_applications",
          joinColumns = @JoinColumn(name = "application_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"))
  List<Job> jobs; // ToDo: テーブル名の前後を変えたい。オーナーである方を前にする。

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "applications")
  List<Task> tasks;
}
