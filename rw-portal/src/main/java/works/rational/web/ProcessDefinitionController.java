package works.rational.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import works.rational.domain.ProcessDefinition;
import works.rational.domain.User;
import works.rational.service.AuthorizationService;
import works.rational.service.LoginUserDetails;
import works.rational.service.ProcessDefinitionService;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/")
public class ProcessDefinitionController {
  private final ProcessDefinitionService processDefinitionService;

  private final AuthorizationService authorizationService;

  public ProcessDefinitionController(ProcessDefinitionService processDefinitionService, AuthorizationService authorizationService) {
    Assert.notNull(processDefinitionService);
    Assert.notNull(authorizationService);
    this.processDefinitionService = processDefinitionService;
    this.authorizationService = authorizationService;
  }

  @GetMapping
  String list(Model model) throws IOException {
    List<ProcessDefinition> processes = processDefinitionService.findAll();
    model.addAttribute("processes", processes);
    return "processes/list";
  }

  @PostMapping(path = "process/activate")
  String activate(@Validated ProcessDefinitionForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) throws IOException {
    ProcessDefinition processDefinition = processDefinitionService.findOne(form.getId());

    // UserRepositoryから取ってくる
    User user = new User();

    System.out.println("[ProcessDefinitionController]" + processDefinitionService);
    System.out.println("[ProcessDefinitionController]" + authorizationService);

    authorizationService.grant(processDefinition, user);

    return "redirect:/";
  }
}
