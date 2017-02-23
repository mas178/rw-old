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
@Table(name = "jobs")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = {"applications", "tasks"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job extends Common {
  @Column(nullable = false)
  String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", nullable = false)
  Company company;

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "jobs")
  List<Application> applications;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "jobs")
  List<Task> tasks;
}
