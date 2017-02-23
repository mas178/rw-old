package works.rational.web;

import org.apache.commons.lang3.StringUtils;
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
import works.rational.domain.*;
import works.rational.service.*;
import works.rational.web.form.TaskForm;

import java.util.List;


@Controller
@RequestMapping("/task")
public class TaskController {
  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  private final TaskService taskService;

  private final ApplicationService applicationService;

  private final ApplicantService applicantService;

  private final CompanyService companyService;

  private final JobService jobService;

  private final UserService userService;

  private final StatusService statusService;

  public TaskController(
          TaskService taskService,
          ApplicationService applicationService,
          ApplicantService applicantService,
          CompanyService companyService,
          JobService jobService,
          UserService userService,
          StatusService statusService) {

    Assert.notNull(taskService);
    Assert.notNull(applicationService);
    Assert.notNull(applicantService);
    Assert.notNull(companyService);
    Assert.notNull(jobService);
    Assert.notNull(userService);
    Assert.notNull(statusService);

    this.taskService = taskService;
    this.applicationService = applicationService;
    this.applicantService = applicantService;
    this.companyService = companyService;
    this.jobService = jobService;
    this.userService = userService;
    this.statusService = statusService;
  }

  @ModelAttribute
  TaskForm setUpForm() {
    return new TaskForm();
  }

  @GetMapping
  String list(Model model) {
    List<Task> tasks = taskService.findAll();
    model.addAttribute("tasks", tasks);
    return "task/list";
  }

  @GetMapping(path = "create")
  String createForm(TaskForm form, Model model) {
    logger.info("form: " + form);
    setUpModel(model, form.getApplicantIds(), form.getCompanyIds());
    return "task/detail";
  }

  @GetMapping(path = "create", params = {"select"})
  public String selectInCreateForm(final TaskForm form, Model model) {
    setUpModel(model, form.getApplicantIds(), form.getCompanyIds());
    return "task/detail";
  }

  @GetMapping(path = "{id}")
  String editForm(@PathVariable String id, TaskForm form, Model model) {
    form.setValues(taskService.findOne(id));
    setUpModel(model, form.getApplicantIds(), form.getCompanyIds());
    return "task/detail";
  }

  @GetMapping(path = "{id}", params = {"select"})
  String selectInEditForm(TaskForm form, Model model) {
    setUpModel(model, form.getApplicantIds(), form.getCompanyIds());
    return "task/detail";
  }

  @PostMapping
  String create(@Validated TaskForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
    return post(null, form, model, result, userDetails);
  }

  @PostMapping(path = "{id}")
  String edit(@PathVariable String id, @Validated TaskForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
    return post(id, form, model, result, userDetails);
  }

  private String post(String id, TaskForm form, Model model, BindingResult result, LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      if (StringUtils.isEmpty(id)) {
        return createForm(form, model);
      } else {
        return editForm(id, form, model);
      }
    }
    taskService.save(formToTask(form), userDetails);
    return "redirect:/task";
  }

  private void setUpModel(Model model, List<String> applicantIds, List<String> companyIds) {
    List<Application> applications = applicationService.findAllByApplicantCompanyIds(applicantIds, companyIds);
    List<Applicant> applicants = applicantService.findAll();
    List<Company> companies = companyService.findAll(); // ToDo: Sort
    List<User> users = userService.findAll();
    List<Job> jobs = jobService.findAllByCompanyIds(companyIds);
    List<Status> statuses = statusService.findAllTaskStatuses();

    model.addAttribute("applications", applications);
    model.addAttribute("applicants", applicants);
    model.addAttribute("companies", companies);
    model.addAttribute("users", users);
    model.addAttribute("jobs", jobs);
    model.addAttribute("statuses", statuses);
  }

  private Task formToTask(TaskForm form) {
    Task task = new Task();
    BeanUtils.copyProperties(form, task);

    task.setUser(userService.findOne(form.getUsername()));
    task.setPlannedTimestamp(Task.Util.strToTimestamp(form.getPlannedTimestamp()));
    task.setActualTimestamp(Task.Util.strToTimestamp(form.getActualTimestamp()));
    task.setApplications(applicationService.findAll(form.getApplicationIds()));
    task.setApplicants(applicantService.findAll(form.getApplicantIds()));
    task.setCompanies(companyService.findAll(form.getCompanyIds()));
    task.setJobs(jobService.findAll(form.getJobIds()));
    task.setStatus(statusService.findOne(form.getStatusId()));

    return task;
  }
}
