package works.rational.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Application;
import works.rational.repository.ApplicationRepository;

import java.util.List;

@Service
@Transactional
public class ApplicationService {
  private final ApplicationRepository applicationRepository;

  public ApplicationService(ApplicationRepository applicationRepository) {
    Assert.notNull(applicationRepository);
    this.applicationRepository = applicationRepository;
  }

  public List<Application> findAll() {
    return applicationRepository.findAll();
  }

  public List<Application> findAll(List<String> applicationIds) {
    return applicationRepository.findAll(applicationIds);
  }

  public List<Application> findAllByApplicantCompanyIds(List<String> applicantIds, List<String> companyIds) {
    if (CollectionUtils.isEmpty(applicantIds) || CollectionUtils.isEmpty(companyIds)) {
      return null;
    } else {
      return applicationRepository.findAllByApplicantCompanyIds(applicantIds, companyIds);
    }
  }

  public Application findOne(String id) {
    return applicationRepository.findOne(id);
  }

  public Application save(Application application, UserDetails userDetails) {
    application.setCreatedUpdatedBy(userDetails.getUsername());
    return applicationRepository.save(application);
  }

  public Application update(Application application, UserDetails userDetails) {
    application.setCreatedUpdatedBy(userDetails.getUsername());
    return applicationRepository.save(application);
  }
}
