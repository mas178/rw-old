package works.rational.web;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProcessDefinitionForm {
  @NotNull
  private String id;
}
