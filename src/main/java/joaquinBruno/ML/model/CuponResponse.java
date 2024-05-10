package joaquinBruno.ML.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CuponResponse {
    @JsonProperty("item_ids")
    private List<String> itemIds;
    private double total;
}
