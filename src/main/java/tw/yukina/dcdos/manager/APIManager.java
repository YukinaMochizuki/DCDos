package tw.yukina.dcdos.manager;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tw.yukina.dcdos.config.TelegramConfig;
import tw.yukina.dcdos.session.TelegramSession;
import tw.yukina.dcdos.util.MessageSupplier;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Service
public class APIManager {

    private LocalDateTime localDateTime;

    private boolean isActive;

    @Value("${telegram.permission.master}")
    private int adminUserId;

    private final Logger logger = LoggerFactory.getLogger(APIManager.class);

    private final ApplicationContext applicationContext;

    private APIManager(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        localDateTime = LocalDateTime.now();
        restart();
    }

    synchronized public void active(){
        if(isActive)return;

        localDateTime = LocalDateTime.now();
        isActive = true;
        sshConnect("start_dcdos_notion_api");
    }

    @Scheduled(fixedRate = 1200000)
    private void checkHibernate(){

        if(LocalDateTime.now().minusHours(1).isBefore(localDateTime))return;

        TelegramConfig telegramConfig = applicationContext.getBean(TelegramConfig.class);
        SendMessage sendMessage = MessageSupplier.getMarkdownFormatBuilder().
                text("API 伺服器進入休眠模式").chatId(String.valueOf(adminUserId)).build();
        telegramConfig.sendMessage(sendMessage);

        sshConnect("stop_dcdos_notion_api");
        isActive = false;
    }

    private void restart(){
        sshConnect("stop_dcdos_notion_api");
        sshConnect("start_dcdos_notion_api");
    }

    private void sshConnect(String command){
        Session session = null;
        ChannelExec channel = null;

        try {
            JSch jSch = new JSch();
            JSch.setConfig("StrictHostKeyChecking", "no");
            jSch.addIdentity("ssh/" + command);
            session = jSch.getSession("gitlab", "production.lan.yukina.tw");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("");
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = responseStream.toString();
            logger.info(responseString);
        } catch (JSchException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }
}
