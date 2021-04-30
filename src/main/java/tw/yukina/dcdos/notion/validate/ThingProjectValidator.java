package tw.yukina.dcdos.notion.validate;

import org.springframework.beans.factory.annotation.Autowired;
import tw.yukina.dcdos.notion.request.ProjectRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class ThingProjectValidator implements ConstraintValidator<ProjectId, String> {

    @Autowired
    private ProjectRequest projectRequest;

    @Override
    public void initialize(ProjectId constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return true;

        return Arrays.stream(Objects.requireNonNull(projectRequest.getAllProject().getBody()))
                .anyMatch(project -> project.getUuid().equals(value));
    }
}
