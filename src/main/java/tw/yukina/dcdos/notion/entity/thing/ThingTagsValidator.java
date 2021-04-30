package tw.yukina.dcdos.notion.entity.thing;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ThingTagsValidator implements ConstraintValidator<ThingTags, String[]> {
    @Override
    public void initialize(ThingTags constraintAnnotation) {

    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return Arrays.stream(value).allMatch(s -> Arrays.asList(Thing.getAllValidTags()).contains(s));
    }
}
