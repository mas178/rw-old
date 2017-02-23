package works.rational.web;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.rational.domain.Group;
import works.rational.domain.Tenant;
import works.rational.domain.User;
import works.rational.service.GroupService;
import works.rational.service.LoginUserDetails;
import works.rational.service.TenantService;
import works.rational.service.UserService;
import works.rational.web.form.GroupForm;

@Controller
@RequestMapping("group")
public class GroupController {

  private final TenantService tenantService;

  private final GroupService groupService;

  private final UserService userService;

  private final Integer DEFAULT_PAGEABLE_SIZE = 20;

  public GroupController(TenantService tenantService, GroupService groupService, UserService userService) {
    Assert.notNull(tenantService);
    Assert.notNull(groupService);
    Assert.notNull(userService);
    this.tenantService = tenantService;
    this.groupService = groupService;
    this.userService = userService;
  }

  @ModelAttribute
  GroupForm setUpForm() {
    GroupForm form = new GroupForm();
    form.setTenantId(getTenant(getCurrentUser()).getId());
    return form;
  }

  @GetMapping
  String list(Model model) {
    Tenant tenant = getTenant(getCurrentUser());
    model.addAttribute("tenant", tenant);
    model.addAttribute("groups", tenant.getGroups());
    return "group/list";
  }

  @GetMapping(path = "create")
  String createForm(GroupForm form) {
    return "group/detail";
  }

  @GetMapping(path = "{id}")
  String editForm(@PathVariable String id, GroupForm form) {
    form.setValues(groupService.findOne(id));
    return "group/detail";
  }

  @PostMapping
  String create(
          @Validated GroupForm form,
          BindingResult result,
          Model model,
          @AuthenticationPrincipal LoginUserDetails userDetails) {
    return post(null, form, result, userDetails);
  }

  @PostMapping(path = "{id}")
  String edit(
          @PathVariable String id,
          @Validated GroupForm form,
          BindingResult result,
          Model model,
          @AuthenticationPrincipal LoginUserDetails userDetails) {
    return post(id, form, result, userDetails);
  }

  private User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    return userService.findOne(username);
  }

  private Tenant getTenant(User user) {
    return user.getGroups().get(0).getTenant();
  }

  private String post(
          @PathVariable String id,
          @Validated GroupForm form,
          BindingResult result,
          @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      if (id == null) {
        return createForm(form);
      } else {
        return editForm(id, form);
      }
    }
    groupService.save(formToGroup(form), userDetails);
    return "redirect:/group";
  }

  private Group formToGroup(GroupForm form) {
    Group group = new Group();
    BeanUtils.copyProperties(form, group);
    group.setTenant(tenantService.findOne(form.getTenantId()));
    group.setUsers(userService.findAll(form.getUsernames()));
    return group;
  }
}
