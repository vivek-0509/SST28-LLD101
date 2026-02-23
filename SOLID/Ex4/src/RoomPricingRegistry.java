import java.util.*;

public class RoomPricingRegistry {
    private final Map<Integer, Double> prices = new HashMap<>();
    private final double defaultPrice;

    public RoomPricingRegistry(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public RoomPricingRegistry register(int roomType, double price) {
        prices.put(roomType, price);
        return this;
    }

    public double getBasePrice(int roomType) {
        return prices.getOrDefault(roomType, defaultPrice);
    }
}
