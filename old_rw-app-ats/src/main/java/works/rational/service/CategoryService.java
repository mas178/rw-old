package works.rational.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import works.rational.domain.Category;
import works.rational.repository.CategoryRepository;

import java.util.List;

@Service
@Transactional
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    Assert.notNull(categoryRepository);
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public List<Category> findAll(List<String> ids) {
    return categoryRepository.findAll(ids);
  }

  public Category findOne(String id) {
    return categoryRepository.findOne(id);
  }

  public Category save(Category category, UserDetails userDetails) {
    category.setCreatedUpdatedBy(userDetails.getUsername());
    return categoryRepository.save(category);
  }

  public Category update(Category category, UserDetails userDetails) {
    category.setCreatedUpdatedBy(userDetails.getUsername());
    return categoryRepository.save(category);
  }
}
