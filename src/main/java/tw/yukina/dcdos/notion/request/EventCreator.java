package tw.yukina.dcdos.notion.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import tw.yukina.dcdos.notion.entity.event.Event;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static tw.yukina.dcdos.notion.request.RequestUtil.getResponse;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventCreator {

    @Getter
    private final Event.EventBuilder eventBuilder;

    @Getter
    @Setter
    private Validator validator;

    private final EventRequest eventRequest;

    public EventCreator(AutowireCapableBeanFactory autowireCapableBeanFactory, EventRequest eventRequest){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();

        eventBuilder = Event.builder();
        this.eventRequest = eventRequest;
    }

    public Set<ConstraintViolation<Event>> validate(){
        return validator.validate(eventBuilder.build());
    }

    public String validateAndCreate(){
        Set<ConstraintViolation<Event>> constraintViolations = validate();
        String validateMessage = "";
        if(!constraintViolations.isEmpty())validateMessage = constraintViolations.iterator().next().getMessage();
        return getResponse(validateMessage, eventRequest.postThing(eventBuilder.build()));
    }
}
