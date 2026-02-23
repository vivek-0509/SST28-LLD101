import java.util.*;

public class Demo04 {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");

        RoomPricingRegistry roomPricing = new RoomPricingRegistry(16000.0);
        roomPricing.register(LegacyRoomTypes.SINGLE, 14000.0);
        roomPricing.register(LegacyRoomTypes.DOUBLE, 15000.0);
        roomPricing.register(LegacyRoomTypes.TRIPLE, 12000.0);

        AddOnPricingRegistry addOnPricing = new AddOnPricingRegistry();
        addOnPricing.register(AddOn.MESS, 1000.0);
        addOnPricing.register(AddOn.LAUNDRY, 500.0);
        addOnPricing.register(AddOn.GYM, 300.0);

        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        HostelFeeCalculator calc = new HostelFeeCalculator(roomPricing, addOnPricing, new FakeBookingRepo());
        calc.process(req);
    }
}
