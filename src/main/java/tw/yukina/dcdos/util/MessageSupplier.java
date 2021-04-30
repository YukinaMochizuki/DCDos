package tw.yukina.dcdos.util;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageSupplier {
    public static SendMessage getMarkdownFormat(){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        return sendMessage;
    }

    public static SendMessage.SendMessageBuilder getMarkdownFormatBuilder(){
        return SendMessage.builder().parseMode(ParseMode.MARKDOWN);
    }
}
