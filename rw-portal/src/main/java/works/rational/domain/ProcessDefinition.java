package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Camunda Process Definition
 *
 * @see <a href="https://docs.camunda.org/manual/7.5/reference/rest/process-definition/">`Process Definition` in Camunda official document</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessDefinition {
  private String id;
  private String key;
  private String category;
  private String description;
  private String name;
  private Integer version;
  private String resource;
  private String deploymentId;
  private String diagram;
  private Boolean suspended;
  private String tenantId;
  private String versionTag;
}
