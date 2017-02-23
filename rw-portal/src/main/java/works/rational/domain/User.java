package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "groups")
public class User implements CamundaDomain {
  @Id
  private String username;

  @JsonIgnore
  private String encodedPassword;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
  private List<Group> groups;

  public User(String username, String encodedPassword) {
    this.username = username;
    this.encodedPassword = encodedPassword;
  }

  public String toJson() {
    String template = "{" +
            "\"profile\":" +
            "  {\"id\": \"%s\"," +
            "  \"firstName\":\"%s\"," +
            "  \"lastName\":\"%s\"," +
            "  \"email\":\"%s\"}," +
            "\"credentials\":" +
            "  {\"password\":\"%s\"}" +
            "}";

    return String.format(template,
            this.getUsername(),
            "TestFirstName",
            "TestLastName",
            "hoge@hoge.hoge",
            this.getEncodedPassword());
  }
}
