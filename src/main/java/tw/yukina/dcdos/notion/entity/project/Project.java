package tw.yukina.dcdos.notion.entity.project;

import lombok.Data;

@Data
public class Project {

    private String title;

    private String uuid;

    private String type;

    private String[] tags;

}
