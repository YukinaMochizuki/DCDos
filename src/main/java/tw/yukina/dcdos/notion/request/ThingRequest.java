package tw.yukina.dcdos.notion.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tw.yukina.dcdos.notion.entity.thing.Thing;

@Component
public class ThingRequest {

    @Value("${notion.api.baseurl}")
    private String baseurl;

    private final RestTemplate restTemplate;

    public ThingRequest(){
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> postThing(Thing thing){
        return restTemplate.postForEntity(baseurl + "/thing", new HttpEntity<>(thing), String.class);
    }
}
