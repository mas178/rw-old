package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Company;
import works.rational.domain.Job;
import works.rational.repository.CompanyRepository;
import works.rational.repository.JobRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobService {
  private final JobRepository jobRepository;
  private final CompanyRepository companyRepository;

  public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
    Assert.notNull(jobRepository);
    Assert.notNull(companyRepository);
    this.jobRepository = jobRepository;
    this.companyRepository = companyRepository;
  }

  public List<Job> findAll() {
    return jobRepository.findAll();
  }

  public List<Job> findAll(List<String> jobIds) {
    return jobRepository.findAll(jobIds);
  }

  public List<Job> findAllByCompanyIds(List<String> companyIds) {
    return companyRepository.findAll(companyIds).stream()
            .map(Company::getJobs)
            .flatMap(List::stream)
            .collect(Collectors.toList());
  }

  public Job findOne(String id) {
    return jobRepository.findOne(id);
  }

  public Job save(Job job, UserDetails userDetails) {
    job.setCreatedUpdatedBy(userDetails.getUsername());
    return jobRepository.save(job);
  }

  public Job update(Job job, UserDetails userDetails) {
    job.setCreatedUpdatedBy(userDetails.getUsername());
    return jobRepository.save(job);
  }
}
