import java.util.*;

public class HostelFeeCalculator {
    private final RoomPricingRegistry roomPricing;
    private final AddOnPricingRegistry addOnPricing;
    private final FakeBookingRepo repo;

    public HostelFeeCalculator(RoomPricingRegistry roomPricing, AddOnPricingRegistry addOnPricing, FakeBookingRepo repo) {
        this.roomPricing = roomPricing;
        this.addOnPricing = addOnPricing;
        this.repo = repo;
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        double base = roomPricing.getBasePrice(req.roomType);
        double add = 0.0;
        for (AddOn a : req.addOns) {
            add += addOnPricing.getPrice(a);
        }
        return new Money(base + add);
    }
}
