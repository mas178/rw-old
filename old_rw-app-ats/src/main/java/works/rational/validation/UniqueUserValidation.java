package works.rational.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import works.rational.repository.UserRepository;
import works.rational.validation.constraints.UniqueUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUserValidation implements ConstraintValidator<UniqueUser, Object> {
  @Autowired
  UserRepository userRepository;
  private String message;

  @Override
  public void initialize(UniqueUser annotation) {
    this.message = annotation.message();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    return userRepository.findOne(value.toString()) == null;
  }
}
