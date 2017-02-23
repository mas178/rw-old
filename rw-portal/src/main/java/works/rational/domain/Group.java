package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "tenant")
public class Group implements CamundaDomain {
  @Id
  private String id;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "groups_users",
          joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
  private List<User> users;

  public Group(String id, String name, List<User> users) {
    this.id = id;
    this.name = name;
    this.users = users;
  }

  @Override
  public String toJson() {
    String template = "{" +
            "\"id\":\"%s\",\n" +
            " \"name\":\"%s\",\n" +
            " \"type\":\"%s\"" +
            "}";

    return String.format(template,
            this.getId(),
            this.getName(),
            "");
  }
}
