package works.rational.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import works.rational.Main;
import works.rational.domain.Applicant;
import works.rational.domain.Status;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
@Repository
public class ApplicantRepositoryTest {
  @Autowired
  private ApplicantRepository applicantRepository;

  @Autowired
  private StatusRepository statusRepository;

  private Applicant applicant;

  @Before
  public void before() {
    Status status = new Status();
    status.setDomain("test");
    status.setName("test status");
    status.setCreatedUpdatedBy("test user");
    statusRepository.save(status);

    applicant = new Applicant();
    applicant.setName("ヨッサリアン");
    applicant.setStatus(status);
    applicant.setDescription("test description");
    applicant.setCreatedUpdatedBy("test user");
  }

  @Test
  public void normal() {
    List<Applicant> applicants = applicantRepository.findAll();
    int count = applicants.size();

    // Save
    Applicant savedApplicant = applicantRepository.save(applicant);
    assertNotNull(savedApplicant);
    assertEquals(count + 1, applicantRepository.count());

    // Delete
    applicantRepository.delete(savedApplicant.getId());
    assertEquals(count, applicantRepository.count());
  }
}
