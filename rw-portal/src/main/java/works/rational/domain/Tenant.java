package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Camunda Tenant
 *
 * @see <a href="https://docs.camunda.org/manual/7.5/reference/rest/tenant/">`Tenant` in Camunda official document</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tenants")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Tenant implements CamundaDomain {
  @Id
  private String id;

  @Column(nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tenant")
  private List<Group> groups;

  public String toJson() {
    String template = "{" +
            "\"id\":\"%s\"," +
            "\"name\":\"%s\"" +
            "}";

    return String.format(template,
            this.getId(),
            this.getName());
  }
}
