package works.rational.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import works.rational.validation.constraints.Match;
import works.rational.validation.constraints.UniqueUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Match(field1 = "password", field2 = "confirmationPassword", fieldName1 = "パスワード", fieldName2 = "確認用パスワード")
public class LoginUserForm {
  @NotNull
  @Size(min = 1, max = 127)
  @UniqueUser
  String username;

  @NotNull
  @Size(min = 1, max = 127)
  String password;

  @NotNull
  @Size(min = 1, max = 127)
  String confirmationPassword;

  @NotNull
  @Size(min = 1, max = 127)
  String tenantName;
}
