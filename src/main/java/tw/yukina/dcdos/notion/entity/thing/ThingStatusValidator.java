package tw.yukina.dcdos.notion.entity.thing;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ThingStatusValidator implements ConstraintValidator<ThingStatus, String> {
    @Override
    public void initialize(ThingStatus constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return true;
        return Arrays.asList(Thing.getAllValidStatus()).contains(value);
    }
}
