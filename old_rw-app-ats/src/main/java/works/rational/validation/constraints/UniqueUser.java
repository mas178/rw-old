package works.rational.validation.constraints;

import works.rational.validation.UniqueUserValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueUserValidation.class})
public @interface UniqueUser {
  String message() default "{com.example.validation.constraints.UniqueUser.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Target({ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public static @interface List {
    UniqueUser[] value();
  }
}