package JejuDorang.JejuDorang.tourspot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class TourSpotService {

    public void getTourSpot(String requestUrl) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            URI uri = new URI(requestUrl);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);


            System.out.println(response.getBody());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
