import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket original = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + original);

        IncidentTicket assigned = service.assign(original, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nAfter updates (new instance): " + escalated);
        System.out.println("Original is unchanged:        " + original);

        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked: " + e.getClass().getSimpleName());
        }
        System.out.println("Tags still intact: " + escalated.getTags());
    }
}
