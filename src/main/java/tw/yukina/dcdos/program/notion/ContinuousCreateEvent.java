package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.event.Event;
import tw.yukina.dcdos.notion.entity.event.EventUtil;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.EventCreator;
import tw.yukina.dcdos.util.ReplyKeyboard;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContinuousCreateEvent extends AbstractNotionCreate{
    @Override
    public String[] getKeyword() {
        return new String[]{"傾聽事件"};
    }

    @Override
    public String getName() {
        return "ContinuousCreateEvent";
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
        stdout("好的，請確認事件類型", getOption("ReplyMarkup", EventUtil.getEventTypeKeyboard()));
        String eventType = getInput();

        stdout("請確認事件標籤", getOption("ReplyMarkup", EventUtil.getEventTagsKeyboard()));
        String[] eventTags = getTags();

        stdout("隸屬的專案？", getOption("ReplyMarkup", ThingUtil.getProjectRelationKeyboard()));
        String projectUuid = getProjectUuid(getInput());

        stdout("開始紀錄，輸入 $done 表示完成",
                getOption("ReplyMarkup", ReplyKeyboard.oneLayerStringKeyboard(new String[]{"$done"})));

        while (true){
            String input = getInput();
            if(input.equals("$done"))break;

            EventCreator eventCreator = applicationContext.getBean(EventCreator.class);
            Event.EventBuilder eventBuilder = eventCreator.getEventBuilder();

            eventBuilder.type(eventType);
            eventBuilder.tags(eventTags);
            eventBuilder.project(projectUuid);

            eventBuilder.title(input);
            eventCreator.validateAndCreate();
        }

        stdout("done");
    }
}
