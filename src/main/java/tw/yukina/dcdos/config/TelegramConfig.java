package tw.yukina.dcdos.config;

import com.google.common.base.Strings;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tw.yukina.dcdos.manager.telegram.TelegramManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class TelegramConfig extends TelegramLongPollingBot {

    @Value("${telegram.token}")
    private String telegramToken;

    @Value("${telegram.username}")
    private String telegramUsername;

    private final TelegramManager telegramManager;

    public TelegramConfig(TelegramManager telegramManager){
        this.telegramManager = telegramManager;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            new Thread(() -> {
                telegramManager.messageInput(update);
            }).start();
        }
    }

    public Message sendMessage(SendMessage message){
        try {
            return execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editMessage(EditMessageText editMessageText){
        try {
            execute(editMessageText); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotStatue() throws IOException {
        URL url = new URL("https://api.telegram.org/bot" + telegramToken + "/getMe");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(content.toString());
        if(json.getBoolean("ok"))return "ok";
        else return json.getString("description");
    }

    public String getTelegramUsername(){
        return telegramUsername;
    }

    @Override
    public String getBotUsername() {
        return telegramUsername;
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }
}
