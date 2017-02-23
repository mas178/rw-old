package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Applicant;
import works.rational.repository.ApplicantRepository;
import works.rational.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class ApplicantService {
  private final ApplicantRepository applicantRepository;

  private final UserRepository userRepository;

  public ApplicantService(ApplicantRepository applicantRepository, UserRepository userRepository) {
    Assert.notNull(applicantRepository);
    Assert.notNull(userRepository);
    this.applicantRepository = applicantRepository;
    this.userRepository = userRepository;
  }

  public List<Applicant> findAll() {
    return applicantRepository.findAll();
  }

  public List<Applicant> findAll(List<String> ids) {
    return applicantRepository.findAll(ids);
  }

  public Applicant findOne(String id) {
    return applicantRepository.findOne(id);
  }

  public Applicant save(Applicant applicant, UserDetails userDetails) {
    applicant.setCreatedUpdatedBy(userDetails.getUsername());
    return applicantRepository.save(applicant);
  }

  public Applicant update(Applicant applicant, UserDetails userDetails) {
    applicant.setCreatedUpdatedBy(userDetails.getUsername());
    return applicantRepository.save(applicant);
  }
}
