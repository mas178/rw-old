package works.rational.validation;

import works.rational.repository.UserRepository;
import works.rational.validation.constraints.UniqueUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUserValidation implements ConstraintValidator<UniqueUser, Object> {
  private String message;

  @Autowired
  UserRepository userRepository;

  @Override
  public void initialize(UniqueUser annotation) {
    this.message = annotation.message();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    return userRepository.findOne(value.toString()) == null;
  }
}
