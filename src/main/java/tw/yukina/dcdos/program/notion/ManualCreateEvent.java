package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.event.Event;
import tw.yukina.dcdos.notion.entity.event.EventUtil;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.EventCreator;

import java.util.UUID;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ManualCreateEvent extends AbstractNotionCreate{

    @Override
    public String[] getKeyword() {
        return new String[]{"建立事件"};
    }

    @Override
    public String getName() {
        return "ManualCreateEvent";
    }

    @Override
    public String getNamespace() {
        return "notion";
    }

    @Override
    public String getDepiction() {
        return null;
    }

    @Override
    public void run() {
        EventCreator eventCreator = applicationContext.getBean(EventCreator.class);
        Event.EventBuilder eventBuilder = eventCreator.getEventBuilder();

        stdout("好的，請說");
        String title = getInput();
        eventBuilder.title(title);

        stdout("請確認所需的 tags，輸入 $done 表示完成",
                getOption("ReplyMarkup", EventUtil.getEventTagsKeyboard()));
        eventBuilder.tags(getTags());

        stdout("請確認事件類型",
                getOption("ReplyMarkup", EventUtil.getEventTypeKeyboard()));
        eventBuilder.type(getInput());

        String project = getProjectAndPrint();
        String uuid = getStatusUuidAndPrint(title);

        new Thread(() -> {
            eventBuilder.project(getProjectUuid(project));
            updateStdout(title + "\nStatus: 已驗證請求", uuid);
            updateStdout(title + "\nStatus: " + eventCreator.validateAndCreate(), uuid);
        }).start();
    }
}
