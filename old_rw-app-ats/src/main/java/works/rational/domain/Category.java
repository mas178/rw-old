package works.rational.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@ToString(exclude = {"companies"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends Common {
  @Column(nullable = false)
  String domain;

  @Column(nullable = false)
  String name;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "categories")
  List<Company> companies;
}
