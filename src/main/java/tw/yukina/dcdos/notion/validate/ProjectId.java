package tw.yukina.dcdos.notion.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ThingProjectValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ProjectId {
    String message() default "Invalid project uuid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}