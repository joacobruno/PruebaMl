package joaquinBruno.ML.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import joaquinBruno.ML.model.CuponResponse;
import joaquinBruno.ML.model.StatsResponse;
import joaquinBruno.ML.repository.ItemRepository;

@Service
public class ItemService {
    private final PriceClient priceClient;
    
    public ItemService(PriceClient priceClient) {
        this.priceClient = priceClient;
    }
    
    public CuponResponse getAffordableItems(List<String> ids, Double amount){
        Map<String, Double> idPriceMap = fetchPrices(ids);
        ItemRepository.getInstance().addFavouriteItems(new ArrayList<>(idPriceMap.keySet()));
        HashMap<String,Double> orderIdPriceMap = orderProductIdMinToMaxValue(idPriceMap);
        return substractValuesAmount(orderIdPriceMap,amount);
    }
    
    public List<StatsResponse> getStats() {
        Map<String, Integer> itemStats = ItemRepository.getInstance().getItemMap();
        Map<String, Integer> firstFiveItemStats = orderMostFavouriteItemToLess(itemStats);
        return getTopFiveItems(firstFiveItemStats);
    }
    
    private List<StatsResponse> getTopFiveItems(Map<String, Integer> itemsStats) {
        List<StatsResponse> statsResponseList = new ArrayList<>();
        List<Map.Entry<String, Integer>> topFiveItems = itemsStats.entrySet().stream().limit(5).collect(Collectors.toList());
        for(Map.Entry<String, Integer> entry : topFiveItems){
            StatsResponse statsResponse = new StatsResponse(entry.getKey(),entry.getValue());
            statsResponseList.add(statsResponse);
        }
        return statsResponseList;
    }
    
    private Map<String, Integer> orderMostFavouriteItemToLess(Map<String, Integer> itemStats) {
        return itemStats.entrySet()
                       .stream()
                       .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                               (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    
    }
    
    private static CuponResponse substractValuesAmount(Map<String, Double> hashMap, double total) {
        List<String> itemIdsSubstracted = new ArrayList<>();
        double totalUse = 0.0;
            for (Map.Entry<String, Double> entry : hashMap.entrySet()) {
                if (entry.getValue() <= total) {
                    String id = entry.getKey();
                    double value = entry.getValue();
                    itemIdsSubstracted.add(id);
                    totalUse += value;
                    total -= value;
                }else{
                    break;
                }
        }
        return new CuponResponse(itemIdsSubstracted, totalUse);
    }
    
    private static HashMap<String, Double> orderProductIdMinToMaxValue(Map<String, Double> idPriceMap) {
        return idPriceMap.entrySet()
                         .stream()
                         .sorted(Map.Entry.comparingByValue())
                         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                 (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
    
    private Map<String, Double> fetchPrices(List<String> ids) {
        return ids.stream()
                  .map(id -> new AbstractMap.SimpleEntry<>(id, priceClient.getItemPrice(id)))
                  .filter(entry -> entry.getValue().isPresent())
                  .collect(Collectors.toMap(
                                                    Map.Entry::getKey,
                                                    entry -> entry.getValue().get(),
                                                    (oldValue, newValue) -> oldValue,
                                                    HashMap::new
                                            ));
    }
    
}
