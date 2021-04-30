package tw.yukina.dcdos.command;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import tw.yukina.dcdos.config.TelegramConfig;

@Command
@Getter
public abstract class AbstractAssistantCommand implements AssistantCommand {
    @Option(hidden = true, required = true, names = {"--ChatId"}, description = "Valid values: ${COMPLETION-CANDIDATES}")
    private int chatId;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    @Lazy
    @Autowired
    private TelegramConfig telegramConfig;
    
    public void sendMessageToChatId(String markdownMessage){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(getChatId()));
        message.setText(markdownMessage);

        telegramConfig.sendMessage(message);
    }
}
