package works.rational.web;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import works.rational.domain.Application;
import works.rational.domain.Category;
import works.rational.domain.Company;
import works.rational.service.CategoryService;
import works.rational.service.CompanyService;
import works.rational.service.LoginUserDetails;
import works.rational.service.StatusService;
import works.rational.web.form.ApplicationForm;
import works.rational.web.form.CompanyForm;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/company")
public class CompanyController {

  private final CompanyService companyService;

  private final CategoryService categoryService;

  private final StatusService statusService;

  public CompanyController(CompanyService companyService, CategoryService categoryService, StatusService statusService) {
    Assert.notNull(companyService);
    Assert.notNull(categoryService);
    Assert.notNull(statusService);
    this.companyService = companyService;
    this.categoryService = categoryService;
    this.statusService = statusService;
  }

  @ModelAttribute
  CompanyForm setUpForm() {
    return new CompanyForm();
  }

  @GetMapping
  String list(Model model) {
    List<Company> companies = companyService.findAll();
    model.addAttribute("companies", companies);
    return "company/list";
  }

  @GetMapping(path = "create")
  String createForm(Model model) {
    setUpModel(model);
    return "company/detail";
  }

  @GetMapping(path = "{id}")
  String editForm(@PathVariable String id, CompanyForm form, Model model) {
    setUpModel(model);
    form.setValues(companyService.findOne(id));
    return "company/detail";
  }

  @PostMapping
  String create(@Validated CompanyForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      return createForm(model);
    }
    companyService.save(formToCompany(form), userDetails);
    return "redirect:/company";
  }

  @PostMapping(path = "{id}")
  String edit(@PathVariable String id, @Validated CompanyForm form, Model model, BindingResult result, @AuthenticationPrincipal LoginUserDetails userDetails) {
    if (result.hasErrors()) {
      return editForm(id, form, model);
    }
    companyService.update(formToCompany(form), userDetails);
    return "redirect:/company";
  }

  private Company formToCompany(CompanyForm form) {
    Company company = new Company();
    BeanUtils.copyProperties(form, company);
    company.setCategories(categoryService.findAll(form.getCategoryIds()));
    company.setStatus(statusService.findOne(form.getStatusId()));
    return company;
  }

  private void setUpModel(Model model){
    model.addAttribute("categories", categoryService.findAll());
    model.addAttribute("statuses", statusService.findAllCompanyStatuses());
  }
}
