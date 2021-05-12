package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.thing.Thing;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.ThingCreator;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ManualCreateActivity extends AbstractNotionCreate{

    @Override
    public String[] getKeyword() {
        return new String[]{"建立活動"};
    }

    @Override
    public String getName() {
        return "ManualCreateActive";
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
        thingBuilder.isEvent(true);

        stdout("好的，請說");
        String title = getInput();
        thingBuilder.title(title);

        stdout("請確認所需的 tags，輸入 $done 表示完成",
                getOption("ReplyMarkup", ThingUtil.getActiveThingTagsKeyboard()));
        thingBuilder.tags(getTags());

        stdout("請告訴我活動日期（yyyy/MM/dd）");
        String deadLineInput = getInput();
        thingBuilder.deadLineStartDate(deadLineInput);

        String project = getProjectAndPrint();
        String uuid = getStatusUuidAndPrint(title);

        new Thread(() -> {
            thingBuilder.project(getProjectUuid(project));
            updateStdout(title + "\nStatus: 已驗證請求", uuid);
            updateStdout(title + "\nStatus: " + thingCreator.validateAndCreate(), uuid);
        }).start();
    }
}
