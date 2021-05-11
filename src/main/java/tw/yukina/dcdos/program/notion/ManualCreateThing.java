package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.thing.Thing;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.ThingCreator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ManualCreateThing extends AbstractNotionCreate {

    @Override
    public String[] getKeyword() {
        return new String[]{"建立代辦"};
    }

    @Override
    public String getName() {
        return "ManualCreateThing";
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
        ThingCreator thingCreator = applicationContext.getBean(ThingCreator.class);

        Thing.ThingBuilder thingBuilder = thingCreator.getThingBuilder();
        thingBuilder.status("New");

        stdout("好的，請說");
        String title = getInput();
        thingBuilder.title(title);

        stdout("請確認所需的 tags，輸入 $done 表示完成",
                getOption("ReplyMarkup", ThingUtil.getTodoThingTagsKeyboard()));
        thingBuilder.tags(getTags());

        stdout("有截止日期嗎？有的話請讓我知道（yyyy/MM/dd）");
        String deadLineInput = getInput();
        if(!deadLineInput.equals("沒有"))thingBuilder.deadLineStartDate(deadLineInput);

        stdout("隸屬的專案？", getOption("ReplyMarkup", ThingUtil.getProjectRelationKeyboard()));
        String project = getInput();

        String uuid = UUID.randomUUID().toString();
        stdout(title + "\nStatus: 已接收請求", uuid);

        new Thread(() -> {
            thingBuilder.project(getProjectUuid(project));
            updateStdout(title + "\nStatus: 已驗證請求", uuid);
            updateStdout(title + "\nStatus: " + thingCreator.validateAndCreate(), uuid);
        }).start();
    }
}
