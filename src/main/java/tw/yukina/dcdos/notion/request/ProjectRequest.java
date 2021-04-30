package tw.yukina.dcdos.notion.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tw.yukina.dcdos.notion.entity.project.Project;

@Component
public class ProjectRequest {

    @Value("${notion.api.baseurl}")
    private String baseurl;

    private final RestTemplate restTemplate;

    public ProjectRequest(){
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<Project[]> getAllProject(){
        return restTemplate.getForEntity(baseurl + "/project", Project[].class);
    }
}
