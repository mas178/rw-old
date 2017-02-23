package works.rational.web;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import works.rational.service.ApplicantService;

import java.io.IOException;


@Controller
@RequestMapping("/")
public class DashBoardController {

  private final ApplicantService applicantService;

  public DashBoardController(ApplicantService applicantService) {
    Assert.notNull(applicantService);
    this.applicantService = applicantService;
  }

  @GetMapping
  String index() throws IOException {
    // ToDo: 求職者数、新規求職者数、サポート率
    return "dashboard/index";
  }
}
