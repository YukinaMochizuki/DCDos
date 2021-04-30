package tw.yukina.dcdos.notion.entity.event;

import lombok.Builder;
import lombok.Data;
import tw.yukina.dcdos.notion.validate.ProjectId;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Event {

    @NotBlank
    private String title;

    @EventType
    private String type;

    @EventTags
    private String[] tags;

    @ProjectId
    private String project;

    public static String[] getAllValidTags() {
        return new String[]{
                "Todo", "Index", "Slack", "Email", "Meeting", "Comment", "Mentioned",
                "Outdated"
        };
    }

    public static String[] getAllValidType() {
        return new String[]{
                "Note", "Event", "Event List", "Exception"
        };
    }
}