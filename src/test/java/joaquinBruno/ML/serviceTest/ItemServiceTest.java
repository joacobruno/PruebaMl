package joaquinBruno.ML.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import joaquinBruno.ML.builder.ItemServiceBuilder;
import joaquinBruno.ML.model.CuponResponse;
import joaquinBruno.ML.model.StatsResponse;
import joaquinBruno.ML.repository.ItemRepository;
import joaquinBruno.ML.service.ItemService;
import joaquinBruno.ML.service.PriceClient;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemServiceTest {

    @Mock
    private PriceClient priceClient;
    
    @BeforeEach
    public void setUp() {
        when(priceClient.getItemPrice("item1")).thenReturn(Optional.of(50.0));
        when(priceClient.getItemPrice("item2")).thenReturn(Optional.of(30.0));
        when(priceClient.getItemPrice("item3")).thenReturn(Optional.of(20.0));
    }
    
    @Test
    public void testGetAffordableItems() {
        
        ItemService itemService =  ItemServiceBuilder.anItemService().withPriceClient(priceClient).build();
  
        List<String> ids = Arrays.asList("item1", "item2", "item3");
        CuponResponse cuponResponse = itemService.getAffordableItems(ids, 80.0);
        
        assertEquals(Arrays.asList("item3", "item2"), cuponResponse.getItemIds());
        assertEquals(50.0, cuponResponse.getTotal());
    }
    
    @Test
    public void testGetStats() {
        
        List<String> favouriteItems = List.of("item1", "item2", "item3","item1","item1","item3");
        ItemRepository.getInstance().addFavouriteItems(favouriteItems);
        
        ItemService itemService =  ItemServiceBuilder.anItemService().withPriceClient(priceClient).build();
        
        List<StatsResponse> statsResponses = itemService.getStats();
        
        assertEquals(3, statsResponses.size());
        assertEquals("item1", statsResponses.get(0).getItemId());
        assertEquals("item3", statsResponses.get(1).getItemId());
        assertEquals("item2", statsResponses.get(2).getItemId());
        assertNotEquals("ItemTest",statsResponses.get(0).getItemId());
    }
}
