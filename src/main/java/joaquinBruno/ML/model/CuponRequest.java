package joaquinBruno.ML.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CuponRequest {
    @JsonProperty("item_ids")
    private List<String> itemIds;
    private double amount;
}
