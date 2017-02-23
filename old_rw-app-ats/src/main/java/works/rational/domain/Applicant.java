package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applicants")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = {"applications", "tasks"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Applicant extends Common {
  @Column(nullable = false, length = 127)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  Status status;

  @Column
  private Date birthDay;

  @Column
  private Integer age;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "applicant")
  private List<Application> applications;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "applicants")
  private List<Task> tasks;

  @Transient
  Integer taskCount = tasks == null ? 0 : tasks.size();

  public Integer getTaskCount(){
    return this.tasks == null ? 0 : this.tasks.size();
  }
}
