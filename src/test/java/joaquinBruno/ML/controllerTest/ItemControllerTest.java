package joaquinBruno.ML.controllerTest;

import joaquinBruno.ML.model.CuponResponse;
import joaquinBruno.ML.model.StatsResponse;
import joaquinBruno.ML.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAffordableItems() throws Exception {

        List<String> itemIds = Arrays.asList("item1", "item2", "item3");
        CuponResponse mockResponse = new CuponResponse(itemIds, 80.0);
        when(itemService.getAffordableItems(itemIds, 80.0)).thenReturn(mockResponse);


        mockMvc.perform(post("/cupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"item_ids\": [\"item1\", \"item2\", \"item3\"], \"amount\": 80.0 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item_ids", hasSize(3)))
                .andExpect(jsonPath("$.total").value(80.0));
    }

    @Test
    public void testFavouriteStats() throws Exception {

        List<StatsResponse> mockStats = Arrays.asList(
                new StatsResponse("item1", 10),
                new StatsResponse("item2", 20),
                new StatsResponse("item3", 15));
        when(itemService.getStats()).thenReturn(mockStats);

        mockMvc.perform(get("/cupon/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].item_id").value("item1"))
                .andExpect(jsonPath("$[0].quantity").value(10))
                .andExpect(jsonPath("$[1].item_id").value("item2"))
                .andExpect(jsonPath("$[1].quantity").value(20))
                .andExpect(jsonPath("$[2].item_id").value("item3"))
                .andExpect(jsonPath("$[2].quantity").value(15));
    }
}
