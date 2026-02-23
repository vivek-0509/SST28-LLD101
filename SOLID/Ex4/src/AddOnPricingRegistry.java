import java.util.*;

public class AddOnPricingRegistry {
    private final Map<AddOn, Double> prices = new HashMap<>();

    public AddOnPricingRegistry register(AddOn addOn, double price) {
        prices.put(addOn, price);
        return this;
    }

    public double getPrice(AddOn addOn) {
        return prices.getOrDefault(addOn, 0.0);
    }
}
