package tw.yukina.dcdos.command.system;

import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;

@Component
@Command(name = "help", version = "help 4.0")
public class TestPicocliCommand extends AbstractAssistantCommand implements Runnable{

//    @Parameters(index = "0", description = "The file whose checksum to calculate.")
//    private int testPara;
//
//    @Option(names = {"-r", "--role"}, description = "Valid values: ${COMPLETION-CANDIDATES}")
//    private Role role;

//    @Autowired
//    private TelegramConfig telegramConfig;

//    @Value("${telegram.permission.default.admin}")
//    private int adminUserId;

//    @Command(name = "sub")
//    private void subCommand(@Parameters(index = "0", description = "The file whose checksum to calculate.") int i){
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(String.valueOf(getChatId()));
//        sendMessage.setText("Hay!! Picocil!!" + i  + role + "*bold*" + "[link](http://google.com)");
//        sendMessage.enableMarkdown(true);
//
//        telegramConfig.sendMessage(sendMessage);
//    }

    @Override
    public String getCommandName() {
        return "check";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[0];
    }

    @Override
    public void run() {
        System.out.println(getChatId());
    }

//    @Override
//    public Integer call() throws Exception {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(String.valueOf(adminUserId));
//        sendMessage.setText("Hay!! Picocil!!" + testPara + "*bold*" + "[link](http://google.com)");
//        sendMessage.enableMarkdown(true);
//
//        telegramConfig.sendMessage(sendMessage);
//
//        return null;
//    }
}
