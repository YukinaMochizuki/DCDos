package tw.yukina.dcdos.notion.entity.event;

import tw.yukina.dcdos.util.ReplyKeyboard;

import java.util.ArrayList;
import java.util.List;

public class EventUtil {
    public static List<List<String>> getEventTypeKeyboard(){
        return ReplyKeyboard.oneLayerStringKeyboard(new String[]{"Note", "Event", "Event List", "Exception"});
    }

    @SuppressWarnings("DuplicatedCode")
    public static List<List<String>> getEventTagsKeyboard(){
        List<List<String>> keyboardLayer = new ArrayList<>();
        List<String> tagsKeyboard0 = new ArrayList<>();
        tagsKeyboard0.add("Todo");
        tagsKeyboard0.add("Index");
        tagsKeyboard0.add("Slack");
        tagsKeyboard0.add("Email");

        List<String> tagsKeyboard1 = new ArrayList<>();
        tagsKeyboard1.add("Meeting");
        tagsKeyboard1.add("Comment");

        List<String> tagsKeyboard2 = new ArrayList<>();
        tagsKeyboard2.add("Mentioned");
        tagsKeyboard2.add("Outdated");

        List<String> tagsKeyboard3 = new ArrayList<>();
        tagsKeyboard3.add("$done");

        keyboardLayer.add(tagsKeyboard0);
        keyboardLayer.add(tagsKeyboard1);
        keyboardLayer.add(tagsKeyboard2);
        keyboardLayer.add(tagsKeyboard3);
        return keyboardLayer;
    }
}
