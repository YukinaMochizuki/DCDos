package tw.yukina.dcdos.program.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.project.Project;
import tw.yukina.dcdos.notion.entity.thing.Thing;
import tw.yukina.dcdos.notion.request.ProjectRequest;
import tw.yukina.dcdos.notion.request.ThingCreator;
import tw.yukina.dcdos.notion.request.ThingRequest;
import tw.yukina.dcdos.program.AbstractProgramCode;

import java.util.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestProgram2 extends AbstractProgramCode {

    @Autowired
    private ProjectRequest projectRequest;

    @Autowired
    private ThingRequest thingRequest;

    @Autowired
    private ThingCreator thingCreator;

    @Override
    public String[] getKeyword() {
        return new String[]{"HelloWorld2"};
    }

    @Override
    public String getName() {
        return "HelloWorld2";
    }

    @Override
    public String getNamespace() {
        return "test";
    }

    @Override
    public String getDepiction() {
        return null;
    }

    @Override
    public void run() {

        Thing.ThingBuilder thingBuilder = thingCreator.getThingBuilder()
                .title("Create in dcdos")
                .tags(new String[]{"coding", "meeting"})
                .status("New")
                .deadLineStartDate("2021-12-10")
                .deadLineEndDate("2021-12-13")
                .deadLineStartTime("12:10")
                .deadLineEndTime("13:20")
                .deadLineTimeEnable(true);

        ResponseEntity<Project[]> responseEntity = projectRequest.getAllProject();
        for(Project project: Objects.requireNonNull(responseEntity.getBody())){
            if(project.getTitle().equals("SITCON 2021")){
                stdout("Title: " + project.getTitle());
                stdout("Tags: " + Arrays.toString(project.getTags()) + " Type: " + project.getType());
                stdout("Uuid:" + project.getUuid());
                thingBuilder.project(project.getUuid());
            }
        }

        stdout(thingCreator.validateAndCreate());
//        stdout("ReplyMarkup", getOption("ReplyMarkup", ReplyKeyboard.oneLayerStringKeyboard(new String[]{"Key1", "Tags"})));
    }
}
