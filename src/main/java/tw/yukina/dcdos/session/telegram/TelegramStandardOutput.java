package tw.yukina.dcdos.session.telegram;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import tw.yukina.dcdos.config.TelegramConfig;
import tw.yukina.dcdos.session.AbstractStandardOutput;

import java.util.Map;

public class TelegramStandardOutput extends AbstractStandardOutput {

    private final Message message;

    private final TelegramConfig telegramConfig;

    public TelegramStandardOutput(TelegramConfig telegramConfig, String uuid, Message message){
        super(uuid);
        super.setMessage(message.getText());
        this.telegramConfig = telegramConfig;
        this.message = message;
        this.setMessage(message.getText());
    }

    @Override
    public void updateMessage(Map<String, Object> messageWithOption) {
        EditMessageText.EditMessageTextBuilder editMessageTextBuilder = EditMessageText.builder()
                .chatId(String.valueOf(message.getChatId()))
                .messageId(message.getMessageId())
                .text((String) messageWithOption.get("Message"));
        telegramConfig.editMessage(editMessageTextBuilder.build());
    }

    @Override
    public void updateMessage(String message){
        EditMessageText.EditMessageTextBuilder editMessageTextBuilder = EditMessageText.builder()
                .chatId(String.valueOf(this.message.getChatId()))
                .messageId(this.message.getMessageId())
                .text((message));
        telegramConfig.editMessage(editMessageTextBuilder.build());
    }
}
