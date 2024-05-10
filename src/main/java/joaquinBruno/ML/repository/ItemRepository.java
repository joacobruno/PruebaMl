package joaquinBruno.ML.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepository {
    private static ItemRepository instance;
    private final Map<String, Integer> itemFavouriteMap;
    
    private ItemRepository() {
        itemFavouriteMap = new HashMap<>();
    }
    
    public static ItemRepository getInstance() {
        if (instance == null) {
            synchronized (ItemRepository.class) {
                if (instance == null) {
                    instance = new ItemRepository();
                }
            }
        }
        return instance;
    }
    
    public Map<String, Integer> getItemMap() {
        return itemFavouriteMap;
    }
    
    public void addFavouriteItems(List<String> ids) {
        ids.forEach(id->itemFavouriteMap.compute(id,(key,value)->(value == null) ? 1 : value + 1));
    }
}

