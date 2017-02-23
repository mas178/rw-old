package works.rational.validation.constraints;

import works.rational.validation.MatchValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MatchValidation.class})
public @interface Match {
  String message() default "{com.example.validation.constraints.Match.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String field1();

  String field2();

  String fieldName1();

  String fieldName2();

  @Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public static @interface List {
    Match[] value();
  }
}