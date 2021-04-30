package tw.yukina.dcdos.session;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import tw.yukina.dcdos.config.TelegramConfig;
import tw.yukina.dcdos.manager.ProgramManager;
import tw.yukina.dcdos.manager.SessionManager;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import static tw.yukina.dcdos.util.ReplyKeyboard.replyKeyboardMarkupBuilder;

@SuppressWarnings("unchecked")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TelegramSession extends AbstractSession{

    @Setter
    private int chat_id;

    @Value("${dcdos.debug}")
    private boolean debugMode;

    private final TelegramConfig telegramConfig;

    public TelegramSession(TelegramConfig telegramConfig, ProgramManager programManager, SessionManager sessionManager) {
        super(programManager, sessionManager);
        this.telegramConfig = telegramConfig;
    }

    @Override
    public void stdoutPrint() {
        Queue<Map<String, Object>> stdout = getActiveProgramExecutor().getProgramController().getRegister().getStdout();

        if(this.isActive()){
            while (stdout.peek() != null){

                Map<String, Object> messageWithOption = stdout.poll();

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chat_id));
                message.setText((String) messageWithOption.get("Message"));

                if(messageWithOption.get("ReplyMarkup") != null){
                    List<List<String>> replyKeyboard = (List<List<String>>) messageWithOption.get("ReplyMarkup");
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

                    keyboardMarkup.setKeyboard(replyKeyboardMarkupBuilder(replyKeyboard));
                    keyboardMarkup.setResizeKeyboard(true);

                    message.setReplyMarkup(keyboardMarkup);
                }

                telegramConfig.sendMessage(message);
            }
        }
    }

    @Override
    public void stderrPrint() {
        Queue<String> stderr = getActiveProgramExecutor().getProgramController().getRegister().getStderr();

        if(this.isActive()){
            while (stderr.peek() != null){
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chat_id));
                message.setText("Error: " + stderr.poll());

                telegramConfig.sendMessage(message);
            }
        }
    }

    @Override
    public void stdinPut(String input) {
        getActiveProgramExecutor().getProgramController().putUserInput(input);
    }
}
