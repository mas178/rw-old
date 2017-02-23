package works.rational.web;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.rational.domain.User;
import works.rational.service.LoginUserDetails;
import works.rational.service.UserService;
import works.rational.web.form.UserForm;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    Assert.notNull(userService);
    this.userService = userService;
  }

  @ModelAttribute
  UserForm setUpForm() {
    return new UserForm();
  }

  @GetMapping
  String list(Model model) {
    List<User> users = userService.findAll();
    model.addAttribute("users", users);
    return "user/list";
  }

  @GetMapping(path = "create")
  String createForm(UserForm form) {
    return "user/detail";
  }

  @GetMapping(path = "{id}")
  String editForm(@PathVariable String id, UserForm form) {
    form.setValues(userService.findOne(id));
    return "user/detail";
  }

  @PostMapping
  String create(@Validated UserForm form, Model model, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {
    return post(null, form, model, result, userDetails);
  }

  @PostMapping(path = "{id}")
  String edit(@PathVariable String id, @Validated UserForm form, Model model, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {
    return post(id, form, model, result, userDetails);
  }

  private String post(@PathVariable String id, @Validated UserForm form, Model model, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      if (id == null) {
        return createForm(form);
      } else {
        return editForm(id, form);
      }
    }
    User user = new User();
    BeanUtils.copyProperties(form, user);
    userService.save(user, userDetails);
    return "redirect:/user";
  }
}
