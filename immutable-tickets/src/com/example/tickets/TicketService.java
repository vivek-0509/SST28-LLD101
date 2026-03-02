package com.example.tickets;

public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        return IncidentTicket.builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tag("NEW")
                .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket ticket) {
        return ticket.toBuilder()
                .priority("CRITICAL")
                .tag("ESCALATED")
                .build();
    }

    public IncidentTicket assign(IncidentTicket ticket, String assigneeEmail) {
        return ticket.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}
