package works.rational.web;

import works.rational.validation.constraints.Match;
import works.rational.validation.constraints.UniqueUser;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Match(field1 = "password", field2 = "confirmationPassword", fieldName1 = "パスワード", fieldName2 = "確認用パスワード")
public class UserForm {
  @NotNull
  @Size(min = 1, max = 127)
  @UniqueUser
  private String username;

  @NotNull
  @Size(min = 1, max = 127)
  private String password;

  @NotNull
  @Size(min = 1, max = 127)
  private String confirmationPassword;
}
