package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Company;
import works.rational.repository.CompanyRepository;

import java.util.List;

@Service
@Transactional
public class CompanyService {
  private final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    Assert.notNull(companyRepository);
    this.companyRepository = companyRepository;
  }

  public List<Company> findAll() {
    return companyRepository.findAll();
  }

  public List<Company> findAll(List<String> ids) {
    return companyRepository.findAll(ids);
  }

  public Company findOne(String id) {
    return companyRepository.findOne(id);
  }

  public Company save(Company company, UserDetails userDetails) {
    company.setCreatedUpdatedBy(userDetails.getUsername());
    return companyRepository.save(company);
  }

  public Company update(Company company, UserDetails userDetails) {
    company.setCreatedUpdatedBy(userDetails.getUsername());
    return companyRepository.save(company);
  }
}
