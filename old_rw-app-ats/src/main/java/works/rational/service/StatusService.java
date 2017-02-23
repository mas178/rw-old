package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Status;
import works.rational.repository.StatusRepository;

import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;
import static works.rational.repository.StatusRepository.StatusSpecifications.domainIs;

@Service
@Transactional
public class StatusService {
  private final StatusRepository statusRepository;

  public StatusService(StatusRepository statusRepository) {
    Assert.notNull(statusRepository);
    this.statusRepository = statusRepository;
  }

  public List<Status> findAll() {
    return statusRepository.findAll();
  }

  public List<Status> findAll(List<String> ids) {
    return statusRepository.findAll(ids);
  }

  public List<Status> findAllApplicationStatuses() {
    return statusRepository.findAll(where(domainIs("application")));
  }

  public List<Status> findAllApplicantStatuses() {
    return statusRepository.findAll(where(domainIs("applicant")));
  }

  public List<Status> findAllCompanyStatuses() {
    return statusRepository.findAll(where(domainIs("company")));
  }

  public List<Status> findAllJobStatuses() {
    return statusRepository.findAll(where(domainIs("job")));
  }

  public List<Status> findAllTaskStatuses() {
    return statusRepository.findAll(where(domainIs("task")));
  }

  public Status findOne(String id) {
    return statusRepository.findOne(id);
  }

  public Status save(Status status, UserDetails userDetails) {
    status.setCreatedUpdatedBy(userDetails.getUsername());
    return statusRepository.save(status);
  }

  public Status update(Status status, UserDetails userDetails) {
    status.setCreatedUpdatedBy(userDetails.getUsername());
    return statusRepository.save(status);
  }
}
