package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import tw.yukina.dcdos.notion.entity.project.Project;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.ProjectRequest;
import tw.yukina.dcdos.program.AbstractProgramCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class AbstractNotionCreate extends AbstractProgramCode {

    @Autowired
    private ProjectRequest projectRequest;

    protected String[] getTags(){
        List<String> tagList = new ArrayList<>();
        while (true){
            String input = getInput();
            if(input.equals("$done"))break;
            tagList.add(input);
        }
        return tagList.toArray(String[]::new);
    }

    protected String getProjectUuid(String name) {
        ResponseEntity<Project[]> responseEntity = projectRequest.getAllProject();
        for (Project project : Objects.requireNonNull(responseEntity.getBody())) {
            if (project.getTitle().equals(name)) {
                return project.getUuid();
            }
        }
        stdout("找不到專案: " + name);
        return "";
    }

    protected String getProjectAndPrint(){
        stdout("隸屬的專案？", getOption("ReplyMarkup", ThingUtil.getProjectRelationKeyboard()));
        return getInput();
    }

    protected String getStatusUuidAndPrint(String title){
        String uuid = UUID.randomUUID().toString();
        stdout(title + "\nStatus: 已接收請求", uuid);
        return uuid;
    }
}
