package tw.yukina.dcdos.session.telegram;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tw.yukina.dcdos.config.TelegramConfig;
import tw.yukina.dcdos.manager.ProgramManager;
import tw.yukina.dcdos.manager.SessionManager;
import tw.yukina.dcdos.session.AbstractSession;
import tw.yukina.dcdos.session.AbstractStandardOutput;

import java.util.*;

@SuppressWarnings("unchecked")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TelegramSession extends AbstractSession {

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
                TelegramSessionUtil.settingSupportMessageOptions(message, messageWithOption);

                Message telegramMessage = telegramConfig.sendMessage(message);
                if(messageWithOption.containsKey("UUID")){
                    TelegramStandardOutput telegramStandardOutput =
                            new TelegramStandardOutput(telegramConfig,
                                    (String) messageWithOption.get("UUID"),
                                    telegramMessage);

                    getActiveProgramExecutor().getProgramController()
                            .getRegister().getStandardOutputs().add(telegramStandardOutput);
                }
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
    public void updateStdoutPrint() {
        List<Map<String, Object>> updateStdoutList = getActiveProgramExecutor().
                getProgramController().getRegister().getUpdateStdout();

        List<Map<String, Object>> updatedStdoutList = new ArrayList<>();

        for(Map<String, Object> updateStdout: updateStdoutList){
            String uuid = (String) updateStdout.get("UUID");

            Optional<AbstractStandardOutput> standardOutputOptional = getActiveProgramExecutor().getProgramController()
                    .getRegister().getStandardOutputs().stream()
                    .filter(abstractStandardOutput -> uuid.equals(abstractStandardOutput.getUuid()))
                    .findAny();

            standardOutputOptional
                    .ifPresent(abstractStandardOutput -> {
                        abstractStandardOutput.updateMessage(updateStdout);
                        updatedStdoutList.add(updateStdout);
                    });
        }

        updateStdoutList.removeAll(updatedStdoutList);
    }

    @Override
    public void stdinPut(String input) {
        getActiveProgramExecutor().getProgramController().putUserInput(input);
    }
}
