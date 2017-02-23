package works.rational.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * Camunda Process Authorization
 *
 * @see <a href="https://docs.camunda.org/manual/7.5/reference/rest/authorization/">`Authorization` in Camunda official document</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Authorization implements CamundaDomain {

  public static final class TYPE {
    public static final Integer GLOBAL = 0;
    public static final Integer GRANT = 1;
    public static final Integer REVOKE = 2;
  }

  public static final class PERMISSION {
    public static final String READ = "READ";
    public static final String READ_TASK = "READ_TASK";
    public static final String UPDATE_TASK = "UPDATE_TASK";
    public static final String CREATE_INSTANCE = "CREATE_INSTANCE";
    public static final String READ_INSTANCE = "READ_INSTANCE";
    public static final String UPDATE_INSTANCE = "UPDATE_INSTANCE";
    public static final String READ_HISTORY = "READ_HISTORY";
    public static final String TASK_WORK = "TASK_WORK";
    public static final String TASK_ASSIGN = "TASK_ASSIGN";
    public static final List<String> PROCESS_DEFINITION = Arrays.asList(
            PERMISSION.READ, PERMISSION.READ_TASK, PERMISSION.UPDATE_TASK, PERMISSION.CREATE_INSTANCE, PERMISSION.READ_INSTANCE,
            PERMISSION.UPDATE_INSTANCE, PERMISSION.READ_HISTORY, PERMISSION.TASK_WORK, PERMISSION.TASK_ASSIGN);
  }

  public static final class RESOURCE_TYPE {
    public static final Integer APPLICATION = 0;
    public static final Integer AUTHORIZATION = 4;
    public static final Integer BATCH = 13;
    public static final Integer DECISION_DEFINITION = 10;
    public static final Integer DEPLOYMENT = 9;
    public static final Integer FILTER = 5;
    public static final Integer GROUP = 2;
    public static final Integer GROUP_MEMBERSHIP = 3;
    public static final Integer PROCESS_DEFINITION = 6;
    public static final Integer PROCESS_INSTANCE = 8;
    public static final Integer TASK = 7;
    public static final Integer TENANT = 11;
    public static final Integer TENANT_MEMBERSHIP = 12;
    public static final Integer USER = 1;
  }


  // The id of the authorization.
  private String id;

  // The type of the authorization. (0=global, 1=grant, 2=revoke).
  private Integer type;

  // An array of strings representing the permissions assigned by this authentication.
  private List<String> permissions;

  // The id of the user this authorization has been created for. The value "\*" represents a global authorization ranging over all users.
  private String userId;

  // The id of the group this authorization has been created for.
  private String groupId;

  // An integer representing the resource type.
  private Integer resourceType;

  // The resource Id. The value "\*" represents an authorization ranging over all instances of a resource.
  private String resourceId;

  // Object	A JSON array containing links to interact with the resource. The links contain only operations that the currently authenticated user would be authorized to perform.
  private Object links;

  public String toJson() {

    String template = "{" +
            "\"type\": %d, " +
            "\"permissions\": [\"%s\"], " +
            "\"userId\": %s, " +
            "\"groupId\": %s, " +
            "\"resourceType\": %d, " +
            "\"resourceId\": \"%s\"" +
            "}";

    return String.format(template,
            this.getType(),
            String.join("\",\"", this.getPermissions()),
            this.getUserId() == null ? "null" : "\"" + this.getUserId() + "\"",
            this.getGroupId() == null ? "null" : "\"" + this.getGroupId() + "\"",
            this.getResourceType(),
            this.getResourceId());
  }
}
