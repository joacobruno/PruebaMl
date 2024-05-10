package joaquinBruno.ML.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsResponse {
    @JsonProperty("item_id")
    private String itemId;
    private Integer quantity;
}
