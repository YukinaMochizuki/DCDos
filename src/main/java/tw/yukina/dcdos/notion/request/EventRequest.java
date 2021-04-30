package tw.yukina.dcdos.notion.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tw.yukina.dcdos.notion.entity.event.Event;

@Component
public class EventRequest {

    @Value("${notion.api.baseurl}")
    private String baseurl;

    private final RestTemplate restTemplate;

    public EventRequest(){
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> postThing(Event event){
        return restTemplate.postForEntity(baseurl + "/event", new HttpEntity<>(event), String.class);
    }
}
