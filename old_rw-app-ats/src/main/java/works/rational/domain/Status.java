package works.rational.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statuses")
@ToString(exclude = {"tasks"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Status extends Common {
  @Column(nullable = false)
  String domain;
  @Column(nullable = false)
  String name;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "status")
  List<Task> tasks;
}
