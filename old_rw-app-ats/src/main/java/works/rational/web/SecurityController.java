package works.rational.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import works.rational.service.LoginService;
import works.rational.web.form.LoginUserForm;

import java.io.IOException;

@Controller
public class SecurityController {
  @Autowired
  LoginService loginService;

  @ModelAttribute
  LoginUserForm setUpForm() {
    return new LoginUserForm();
  }

  @GetMapping(path = "loginForm")
  String loginForm() {
    return "securities/loginForm";
  }

  @GetMapping(path = "register")
  String register(LoginUserForm form) {
    return "securities/registerForm";
  }

  @PostMapping(path = "register")
  String register(@Validated LoginUserForm form, BindingResult result) throws IOException {
    if (result.hasErrors()) {
      return register(form);
    }

    loginService.save(form.getUsername(), form.getPassword(), form.getTenantName());

    return "securities/registerConfirmation";
  }
}
