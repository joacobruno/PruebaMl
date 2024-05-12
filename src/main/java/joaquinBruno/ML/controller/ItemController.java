package joaquinBruno.ML.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import joaquinBruno.ML.model.CuponRequest;
import joaquinBruno.ML.model.CuponResponse;
import joaquinBruno.ML.model.StatsResponse;
import joaquinBruno.ML.service.ItemService;

@Controller
@RequestMapping(path="/cupon")
public class ItemController {
    private final ItemService itemService;
    
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @PostMapping
    public ResponseEntity<CuponResponse> affordableItems(@RequestBody CuponRequest cuponRequest){
        if (cuponRequest.getItemIds() == null || cuponRequest.getAmount() ==  null) {
            return ResponseEntity.badRequest().build();
        }
        CuponResponse cuponResponse = itemService.getAffordableItems(cuponRequest.getItemIds(),cuponRequest.getAmount());
        return ResponseEntity.ok(cuponResponse);
    }
    
    @GetMapping(value="/stats")
    public ResponseEntity<List<StatsResponse>> favouriteStats(){
        return ResponseEntity.ok(itemService.getStats());
    }
}
