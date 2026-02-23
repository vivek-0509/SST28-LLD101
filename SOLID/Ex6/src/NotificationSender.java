public abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    public final SendResult send(Notification n) {
        if (n == null) {
            return SendResult.fail("notification must not be null");
        }
        return doSend(n);
    }

    protected abstract SendResult doSend(Notification n);
}
