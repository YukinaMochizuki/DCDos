package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.thing.Thing;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.ThingCreator;

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
        thingBuilder.title(getInput());

        stdout("請確認所需的 tags，輸入 $done 表示完成",
                getOption("ReplyMarkup", ThingUtil.getTodoThingTagsKeyboard()));
        thingBuilder.tags(getTags());

        stdout("有截止日期嗎？有的話請讓我知道（yyyy/MM/dd）");
        String deadLineInput = getInput();
        if(!deadLineInput.equals("沒有"))thingBuilder.deadLineStartDate(deadLineInput);

        stdout("隸屬的專案？", getOption("ReplyMarkup", ThingUtil.getProjectRelationKeyboard()));
        thingBuilder.project(getProjectUuid(getInput()));

        stdout("瞭解，正在處理請求...");
        stdout(thingCreator.validateAndCreate());
    }
}
