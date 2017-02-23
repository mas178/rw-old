package works.rational.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.rational.domain.Job;
import works.rational.service.CompanyService;
import works.rational.service.JobService;
import works.rational.service.LoginUserDetails;
import works.rational.service.StatusService;
import works.rational.web.form.JobForm;

import java.util.List;

@Controller
@RequestMapping("/job")
public class JobController {
  private static final Logger logger = LoggerFactory.getLogger(JobController.class);

  private final JobService jobService;

  private final CompanyService companyService;

  private final StatusService statusService;

  public JobController(JobService jobService, CompanyService companyService, StatusService statusService) {
    Assert.notNull(jobService);
    Assert.notNull(companyService);
    Assert.notNull(statusService);
    this.jobService = jobService;
    this.companyService = companyService;
    this.statusService = statusService;
  }

  @ModelAttribute
  JobForm setUpForm() {
    return new JobForm();
  }

  @GetMapping
  String list(Model model) {
    logger.debug("");

    List<Job> jobs = jobService.findAll();
    model.addAttribute("jobs", jobs);
    return "job/list";
  }

  @GetMapping(path = "create")
  String createForm(Model model) {
    logger.debug("");
    setUpMode(model);
    return "job/detail";
  }

  @GetMapping(path = "{id}")
  String editForm(@PathVariable String id, JobForm form, Model model) {
    logger.debug("id: " + id + ", form: " + form);
    form.setJob(jobService.findOne(id));
    setUpMode(model);
    return "job/detail";
  }

  @PostMapping
  String create(@Validated JobForm form, Model model, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {
    logger.debug("form: " + form);

    if (result.hasErrors()) {
      return createForm(model);
    }
    jobService.save(formToJob(form), userDetails);
    return "redirect:/job";
  }

  @PostMapping(path = "{id}")
  String edit(@PathVariable String id, @Validated JobForm form, Model model, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {
    logger.debug("id: " + id + ", form: " + form);

    if (result.hasErrors()) {
      return editForm(id, form, model);
    }
    jobService.update(formToJob(form), userDetails);
    return "redirect:/job";
  }

  private void setUpMode(Model model) {
    model.addAttribute("companies", companyService.findAll());
    model.addAttribute("statuses", statusService.findAllJobStatuses());
  }

  private Job formToJob(JobForm form) {
    Job job = new Job();
    BeanUtils.copyProperties(form, job);
    job.setCompany(companyService.findOne(form.getCompanyId()));
    job.setStatus(statusService.findOne(form.getStatusId()));
    return job;
  }
}
