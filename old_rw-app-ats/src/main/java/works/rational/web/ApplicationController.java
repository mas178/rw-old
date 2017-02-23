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
import works.rational.domain.Application;
import works.rational.domain.Company;
import works.rational.domain.Job;
import works.rational.service.*;
import works.rational.web.form.ApplicationForm;

import java.util.List;

@Controller
@RequestMapping("/application")
public class ApplicationController {

  private final ApplicationService applicationService;

  private final ApplicantService applicantService;

  private final CompanyService companyService;

  private final JobService jobService;

  private final StatusService statusService;

  public ApplicationController(
          ApplicationService applicationService,
          ApplicantService applicantService,
          CompanyService companyService,
          JobService jobService,
          StatusService statusService) {

    Assert.notNull(applicationService);
    Assert.notNull(applicantService);
    Assert.notNull(companyService);
    Assert.notNull(jobService);
    Assert.notNull(statusService);

    this.applicationService = applicationService;
    this.applicantService = applicantService;
    this.companyService = companyService;
    this.jobService = jobService;
    this.statusService = statusService;
  }

  @ModelAttribute
  ApplicationForm setUpForm() {
    return new ApplicationForm();
  }

  @GetMapping
  String list(Model model) {
    List<Application> applications = applicationService.findAll();
    model.addAttribute("applications", applications);
    return "application/list";
  }

  @GetMapping(path = "create")
  String createForm(Model model) {
    setUpModel(model, null);
    return "application/detail";
  }

  @GetMapping(path = "create", params = {"select"})
  public String selectInCreateForm(final ApplicationForm form, Model model) {
    setUpModel(model, form.getCompanyId());
    return "application/detail";
  }

  @GetMapping(path = "{id}")
  String editForm(@PathVariable String id, ApplicationForm form, Model model) {
    form.setValues(applicationService.findOne(id));
    setUpModel(model, form.getCompanyId());
    return "application/detail";
  }

  @GetMapping(path = "{id}", params = {"select"})
  String selectInEditForm(ApplicationForm form, Model model) {
    setUpModel(model, form.getCompanyId());
    return "application/detail";
  }

  @PostMapping
  String create(@Validated ApplicationForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      return createForm(model);
    }
    applicationService.save(formToApplication(form), userDetails);
    return "redirect:/application";
  }

  @PostMapping(path = "{id}")
  String edit(@PathVariable String id, @Validated ApplicationForm form, Model model, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      return editForm(id, form, model);
    }
    applicationService.update(formToApplication(form), userDetails);
    return "redirect:/application";
  }

  private Application formToApplication(ApplicationForm form) {
    Application application = new Application();
    BeanUtils.copyProperties(form, application);

    application.setApplicant(applicantService.findOne(form.getApplicantId()));
    application.setCompany(companyService.findOne(form.getCompanyId()));
    application.setJobs(jobService.findAll(form.getJobIds()));
    application.setStatus(statusService.findOne(form.getStatusId()));

    return application;
  }

  private void setUpModel(Model model, String companyId) {
    List<Company> companies = companyService.findAll(); // ToDo: Sort
    List<Job> jobs = (companyId == null) ? companies.get(0).getJobs() : companyService.findOne(companyId).getJobs();

    model.addAttribute("applicants", applicantService.findAll());
    model.addAttribute("companies", companies);
    model.addAttribute("jobs", jobs);
    model.addAttribute("statuses", statusService.findAllApplicationStatuses());
  }
}
