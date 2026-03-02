package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;

    private IncidentTicket(Builder builder) {
        this.id = builder.id;
        this.reporterEmail = builder.reporterEmail;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.tags = Collections.unmodifiableList(new ArrayList<>(builder.tags));
        this.assigneeEmail = builder.assigneeEmail;
        this.customerVisible = builder.customerVisible;
        this.slaMinutes = builder.slaMinutes;
        this.source = builder.source;
    }

    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; }
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }

    public static class Builder {
        private String id;
        private String reporterEmail;
        private String title;
        private String description;
        private String priority;
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible;
        private Integer slaMinutes;
        private String source;

        public Builder() {}

        private Builder(IncidentTicket ticket) {
            this.id = ticket.id;
            this.reporterEmail = ticket.reporterEmail;
            this.title = ticket.title;
            this.description = ticket.description;
            this.priority = ticket.priority;
            this.tags = new ArrayList<>(ticket.tags);
            this.assigneeEmail = ticket.assigneeEmail;
            this.customerVisible = ticket.customerVisible;
            this.slaMinutes = ticket.slaMinutes;
            this.source = ticket.source;
        }

        public Builder id(String id) { this.id = id; return this; }
        public Builder reporterEmail(String email) { this.reporterEmail = email; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String desc) { this.description = desc; return this; }
        public Builder priority(String priority) { this.priority = priority; return this; }
        public Builder assigneeEmail(String email) { this.assigneeEmail = email; return this; }
        public Builder customerVisible(boolean visible) { this.customerVisible = visible; return this; }
        public Builder slaMinutes(Integer minutes) { this.slaMinutes = minutes; return this; }
        public Builder source(String source) { this.source = source; return this; }

        public Builder tags(List<String> tags) {
            this.tags = new ArrayList<>(tags);
            return this;
        }

        public Builder tag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            if (assigneeEmail != null && !assigneeEmail.trim().isEmpty()) {
                Validation.requireEmail(assigneeEmail, "assigneeEmail");
            }
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");
            return new IncidentTicket(this);
        }
    }
}
