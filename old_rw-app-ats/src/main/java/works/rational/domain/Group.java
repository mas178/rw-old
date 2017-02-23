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
@Table(name = "groups")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "tenant")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group extends Common {
  @Column(nullable = false)
  String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  Tenant tenant;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "groups_users",
          joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
  List<User> users;

  public Group(String id, String name, List<User> users) {
    this.setId(id);
    this.name = name;
    this.users = users;
  }
}
