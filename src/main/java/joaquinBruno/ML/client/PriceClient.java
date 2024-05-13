package joaquinBruno.ML.client;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import joaquinBruno.ML.model.ItemResponse;

@Service
public class PriceClient {
    private static String apiUrl = "https://api.mercadolibre.com/items/";
    
    public Optional<Double> getItemPrice(String itemId){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<ItemResponse> responseEntity = restTemplate.getForEntity(apiUrl + itemId, ItemResponse.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                ItemResponse itemResponse = responseEntity.getBody();
                if (itemResponse != null) {
                    return Optional.of(itemResponse.getPrice());
                }
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("Item not found: " + itemId);
            } else {
                System.out.println("Error fetching item price for ID " + itemId + ": " + e.getMessage());
            }
        }
        return Optional.empty();
    }
}
