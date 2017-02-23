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
@Table(name = "companies")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = {"jobs", "applications", "tasks"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company extends Common {
  @Column(nullable = false)
  String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  Status status;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "companies_categories",
          joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
  List<Category> categories;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "company")
  List<Job> jobs;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "company")
  List<Application> applications;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "companies")
  List<Task> tasks;
}
