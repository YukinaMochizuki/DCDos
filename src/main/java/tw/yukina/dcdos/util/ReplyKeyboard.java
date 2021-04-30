package tw.yukina.dcdos.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboard {

    public static List<KeyboardRow> replyKeyboardMarkupBuilder(List<List<String>> keyboardRowStringList){
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        for(List<String> stringList: keyboardRowStringList){
            KeyboardRow keyboardRow = new KeyboardRow();
            for(String buttonString: stringList){
                keyboardRow.add(buttonString);
            }
            keyboardRowList.add(keyboardRow);
        }

        return keyboardRowList;
    }


    public static List<List<String>> oneLayerStringKeyboard(String[] stringKeyboard){
        List<List<String>> stringKeyboardList = new ArrayList<>();
        stringKeyboardList.add(List.of(stringKeyboard));
        return stringKeyboardList;
    }
}
