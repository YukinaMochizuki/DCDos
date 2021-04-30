package tw.yukina.dcdos.notion.entity.thing;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ThingTagsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ThingTags {
    String message() default "Invalid tags";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}