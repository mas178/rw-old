package works.rational.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.Main;
import works.rational.domain.*;
import works.rational.repository.ApplicantRepository;
import works.rational.repository.ApplicationRepository;
import works.rational.repository.CompanyRepository;
import works.rational.repository.JobRepository;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@Service
public class ApplicationServiceTest {
  @Autowired
  private ApplicationService applicationService;
  @Autowired
  private ApplicantService applicantService;
  @Autowired
  private CompanyService companyService;
  @Autowired
  private JobService jobService;
  @Autowired
  private StatusService statusService;
  @Autowired
  private ApplicationRepository applicationRepository;
  @Autowired
  private ApplicantRepository applicantRepository;
  @Autowired
  private CompanyRepository companyRepository;
  @Autowired
  private JobRepository jobRepository;

  private Application application;

  private UserDetails userDetails;

  @Before
  public void before() {
    clearDb();

    User user = new User();
    user.setUsername("testUserName");
    user.setEncodedPassword("password");
    user.setDescription("test user description");

    userDetails = new LoginUserDetails(user);

    Status status = new Status();
    status.setName("test status");
    status.setDomain("test");
    status.setCreatedUpdatedBy("test user");
    statusService.save(status, userDetails);

    Applicant applicant = new Applicant();
    applicant.setName("test applicant name");
    applicant.setStatus(status);
    applicantService.save(applicant, userDetails);

    Company company = new Company();
    company.setName("test name");
    company.setStatus(status);
    companyService.save(company, userDetails);

    Job job = new Job();
    job.setCompany(company);
    job.setName("test name");
    job.setStatus(status);
    jobService.save(job, userDetails);

    application = new Application();
    application.setDescription("TestTestTestTestTestTest");
    application.setCreatedUpdatedBy("testUser");
    application.setApplicant(applicant);
    application.setCompany(company);
    application.setStatus(status);
    application.setJobs(Arrays.asList(job));
  }

  @Test
  public void save() {
    assertEquals(0, applicationService.findAll().size());

    Application resultApplication = applicationService.save(application, userDetails);

    assertNotNull(resultApplication);
    assertSame(application.getCompany().getId(), resultApplication.getCompany().getId());
    assertSame(application.getJobs().get(0).getId(), resultApplication.getJobs().get(0).getId());
    assertSame(application.getId(), resultApplication.getId());

    assertEquals(1, applicationService.findAll().size());
  }

  @After
  public void after() {
    clearDb();
  }

  private void clearDb() {
    applicationRepository.deleteAllInBatch();
    assertEquals(0, applicationRepository.count());
    jobRepository.deleteAllInBatch();
    assertEquals(0, jobRepository.count());
    companyRepository.deleteAllInBatch();
    assertEquals(0, companyRepository.count());
    applicantRepository.deleteAllInBatch();
    assertEquals(0, applicantRepository.count());
  }
}
