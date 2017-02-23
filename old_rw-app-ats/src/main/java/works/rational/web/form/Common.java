package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Common {
  @Size(max = 30)
  String id;
  @Size(max = 800)
  String description;
  String createdBy;
  String updatedBy;
  Date createdAt;
  Date updatedAt;
}
