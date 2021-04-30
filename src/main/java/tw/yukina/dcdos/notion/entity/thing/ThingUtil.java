package tw.yukina.dcdos.notion.entity.thing;

import java.util.ArrayList;
import java.util.List;

public class ThingUtil {
    @SuppressWarnings("DuplicatedCode")
    public static List<List<String>> getTodoThingTagsKeyboard(){
        List<List<String>> tagsKeyboardLayer = new ArrayList<>();

        List<String> tagsKeyboard0 = new ArrayList<>();
        tagsKeyboard0.add("計畫");
        tagsKeyboard0.add("行政");
        tagsKeyboard0.add("學習");
        tagsKeyboard0.add("溝通");
        tagsKeyboard0.add("寄信");
        tagsKeyboard0.add("課程");
        tagsKeyboard0.add("作業");

        List<String> tagsKeyboard1 = new ArrayList<>();
        tagsKeyboard1.add("等待回覆");
        tagsKeyboard1.add("關注");
        tagsKeyboard1.add("Listener");
        tagsKeyboard1.add("Inbox");

        List<String> tagsKeyboard2 = new ArrayList<>();
        tagsKeyboard2.add("一顆蕃茄");
        tagsKeyboard2.add("兩顆蕃茄");
        tagsKeyboard2.add("三顆蕃茄");
        tagsKeyboard2.add("四顆蕃茄");

        List<String> tagsKeyboard3 = new ArrayList<>();
        tagsKeyboard3.add("$done");

        tagsKeyboardLayer.add(tagsKeyboard0);
        tagsKeyboardLayer.add(tagsKeyboard1);
        tagsKeyboardLayer.add(tagsKeyboard2);
        tagsKeyboardLayer.add(tagsKeyboard3);
        return tagsKeyboardLayer;
    }

    public static List<List<String>> getActiveThingTagsKeyboard(){
        List<List<String>> tagsKeyboardLayer = new ArrayList<>();

        List<String> tagsKeyboard0 = new ArrayList<>();
        tagsKeyboard0.add("meeting");
        tagsKeyboard0.add("報告");
        tagsKeyboard0.add("時間點");
        tagsKeyboard0.add("放假");
        tagsKeyboard0.add("考試");

        List<String> tagsKeyboard1 = new ArrayList<>();
        tagsKeyboard1.add("重要考試");
        tagsKeyboard1.add("考試參考日");
        tagsKeyboard1.add("申請相關");
        tagsKeyboard1.add("活動日期");

        List<String> tagsKeyboard2 = new ArrayList<>();
        tagsKeyboard2.add("$done");

        tagsKeyboardLayer.add(tagsKeyboard0);
        tagsKeyboardLayer.add(tagsKeyboard1);
        tagsKeyboardLayer.add(tagsKeyboard2);
        return tagsKeyboardLayer;
    }

    public static List<List<String>> getProjectRelationKeyboard(){
        List<List<String>> projectKeyboardLayer = new ArrayList<>();
        List<String> projectKeyboard0 = new ArrayList<>();
        projectKeyboard0.add("生活");
        projectKeyboard0.add("校務");
        projectKeyboard0.add("SITCON 2021");

        List<String> projectKeyboard1 = new ArrayList<>();
        projectKeyboard1.add("SITCON Camp 2021");
        projectKeyboard1.add("HITCON 2021");
        projectKeyboard1.add("OCF");

        projectKeyboardLayer.add(projectKeyboard0);
        projectKeyboardLayer.add(projectKeyboard1);
        return projectKeyboardLayer;
    }
}
