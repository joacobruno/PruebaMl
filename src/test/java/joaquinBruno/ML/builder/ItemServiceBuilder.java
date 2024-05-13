package joaquinBruno.ML.builder;

import joaquinBruno.ML.service.ItemService;
import joaquinBruno.ML.client.PriceClient;

public final class ItemServiceBuilder {
    private PriceClient priceClient;
    
    private ItemServiceBuilder() {
    }
    
    public static ItemServiceBuilder anItemService() {
        return new ItemServiceBuilder();
    }
    
    public ItemServiceBuilder withPriceClient(PriceClient priceClient) {
        this.priceClient = priceClient;
        return this;
    }
    
    public ItemService build() {
        return new ItemService(priceClient);
    }
}
