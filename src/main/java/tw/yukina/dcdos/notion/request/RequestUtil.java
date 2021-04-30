package tw.yukina.dcdos.notion.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

public class RequestUtil {
    public static String getResponse(String message, ResponseEntity<String> stringResponseEntity) {
        if(!message.equals("")) return  "Self validate failed: " + message;

        try {
            return stringResponseEntity.getStatusCode().toString() + ", message: " + stringResponseEntity.getBody();
        } catch (HttpServerErrorException httpServerErrorException){
            return httpServerErrorException.getRawStatusCode() + " " + httpServerErrorException.getStatusText() + ",\n" +
                    "message: " + httpServerErrorException.getResponseBodyAsString();
        }
    }
}
