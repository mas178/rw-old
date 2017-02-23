package works.rational.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import works.rational.domain.User;
import works.rational.service.LoginUserDetailsService;

import java.io.IOException;

@Controller
public class SecurityController {
  @Autowired
  LoginUserDetailsService userDetailsService;

  @ModelAttribute
  UserForm setUpForm() {
    return new UserForm();
  }

  @GetMapping(path = "loginForm")
  String loginForm() {
    return "securities/loginForm";
  }

  @GetMapping(path = "register")
  String register(UserForm form) {
    return "securities/registerForm";
  }

  @PostMapping(path = "register")
  String register(@Validated UserForm form, BindingResult result, Model model) throws IOException {
    if (result.hasErrors()) {
      return register(form);
    }

    userDetailsService.create(transformUserForm(form));

    return "securities/registerConfirmation";
  }

  private User transformUserForm(UserForm form) {
    String username = form.getUsername();
    String encodedPassword = new Pbkdf2PasswordEncoder().encode(form.getPassword());

    User user = new User();
    user.setUsername(username);
    user.setEncodedPassword(encodedPassword);
    return user;
  }
}
