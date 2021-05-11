package tw.yukina.dcdos.session.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;
import java.util.Map;

import static tw.yukina.dcdos.util.ReplyKeyboard.replyKeyboardMarkupBuilder;

@SuppressWarnings("unchecked")
public class TelegramSessionUtil {
    public static SendMessage settingSupportMessageOptions(SendMessage message, Map<String, Object> messageWithOption){
        message.setText((String) messageWithOption.get("Message"));

        if(messageWithOption.get("ReplyMarkup") != null){
            message.setReplyMarkup(settingReplyMarkupOption((List<List<String>>)
                    messageWithOption.get("ReplyMarkup")));
        }
        return message;
    }

    public static ReplyKeyboardMarkup settingReplyMarkupOption(List<List<String>> replyKeyboard){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setKeyboard(replyKeyboardMarkupBuilder(replyKeyboard));
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }
}
