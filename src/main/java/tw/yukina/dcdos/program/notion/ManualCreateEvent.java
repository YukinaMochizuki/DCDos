package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.event.Event;
import tw.yukina.dcdos.notion.entity.event.EventUtil;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.EventCreator;

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
        eventBuilder.title(getInput());

        stdout("請確認所需的 tags，輸入 $done 表示完成",
                getOption("ReplyMarkup", EventUtil.getEventTagsKeyboard()));
        eventBuilder.tags(getTags());

        stdout("請確認事件類型",
                getOption("ReplyMarkup", EventUtil.getEventTypeKeyboard()));
        eventBuilder.type(getInput());

        stdout("隸屬的專案？", getOption("ReplyMarkup", ThingUtil.getProjectRelationKeyboard()));
        eventBuilder.project(getProjectUuid(getInput()));

        stdout("瞭解，正在處理請求...");
        stdout(eventCreator.validateAndCreate());
    }
}
