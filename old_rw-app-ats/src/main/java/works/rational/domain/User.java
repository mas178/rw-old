package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = {"groups", "tenant", "tasks"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
  @Id
  String username;

  @JsonIgnore
  @Column
  String encodedPassword;

  @Column(length = 1200)
  String description;

  @Column(nullable = false, updatable = false)
  String createdBy;

  @Column(nullable = false)
  String updatedBy;

  @Column(nullable = false, updatable = false)
  Date createdAt;

  @Column(nullable = false)
  Date updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  Tenant tenant;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "users")
  List<Group> groups;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "user")
  List<Task> tasks;

  public User(String username) {
    this.username = username;
  }

  public void setTimeStamp(String username) {
    if (this.getCreatedBy() == null) {
      this.setCreatedBy(username);
    }
    if (this.getCreatedAt() == null) {
      this.setCreatedAt(new Date());
    }
    this.setUpdatedBy(username);
    this.setUpdatedAt(new Date());
  }
}
