package tw.yukina.dcdos.notion.entity.event;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EventTagsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTags {
    String message() default "Invalid tags";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}