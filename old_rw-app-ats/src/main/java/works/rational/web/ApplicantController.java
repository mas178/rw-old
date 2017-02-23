package works.rational.web;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.rational.domain.Applicant;
import works.rational.service.ApplicantService;
import works.rational.service.LoginUserDetails;
import works.rational.service.StatusService;
import works.rational.web.form.ApplicantForm;

import java.util.List;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {

  private final ApplicantService applicantService;

  private final StatusService statusService;

  public ApplicantController(ApplicantService applicantService, StatusService statusService) {
    Assert.notNull(applicantService);
    Assert.notNull(statusService);
    this.applicantService = applicantService;
    this.statusService = statusService;
  }

  @ModelAttribute
  ApplicantForm setUpForm() {
    return new ApplicantForm();
  }

  @GetMapping
  String list(Model model) {
    List<Applicant> applicants = applicantService.findAll();
    model.addAttribute("applicants", applicants);
    return "applicant/list";
  }

  @GetMapping(path = "create")
  String createForm(Model model) {
    setUpModel(model);
    return "applicant/detail";
  }

  @GetMapping(path = "{id}")
  String editForm(@PathVariable String id, ApplicantForm form, Model model) {
    form.setValues(applicantService.findOne(id));
    setUpModel(model);
    return "applicant/detail";
  }

  @PostMapping
  String create(@Validated ApplicantForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      return createForm(model);
    }
    applicantService.save(formToApplicant(form), userDetails);
    return "redirect:/applicant";
  }

  @PostMapping(path = "{id}")
  String edit(@PathVariable String id, @Validated ApplicantForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      return editForm(id, form, model);
    }
    applicantService.update(formToApplicant(form), userDetails);
    return "redirect:/applicant";
  }

  private void setUpModel(Model model) {
    model.addAttribute("statuses", statusService.findAllApplicantStatuses());
  }

  private Applicant formToApplicant(ApplicantForm form) {
    Applicant applicant = new Applicant();
    BeanUtils.copyProperties(form, applicant);

    applicant.setStatus(statusService.findOne(form.getStatusId()));

    return applicant;
  }
}
