package works.rational.validation;

import works.rational.validation.constraints.Match;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchValidation implements ConstraintValidator<Match, Object> {
  private String field1;
  private String field2;
  private String fieldName1;
  private String fieldName2;
  private String message;

  @Override
  public void initialize(Match annotation) {
    this.field1 = annotation.field1();
    this.field2 = annotation.field2();
    this.fieldName1 = annotation.fieldName1();
    this.fieldName2 = annotation.fieldName2();
    this.message = annotation.message();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(value);
    String fieldValue1 = (String) beanWrapper.getPropertyValue(this.field1);
    String fieldValue2 = (String) beanWrapper.getPropertyValue(this.field2);

    if (fieldValue1 == null || fieldValue2 == null || fieldValue1.equals(fieldValue2)) {
      return true;
    } else {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message)
              .addPropertyNode(this.field2)
              .addConstraintViolation();
      return false;
    }
  }
}