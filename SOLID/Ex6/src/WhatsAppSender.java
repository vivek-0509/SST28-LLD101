public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    protected SendResult doSend(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) {
            audit.add("WA failed");
            return SendResult.fail("phone must start with + and country code");
        }
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
        return SendResult.ok();
    }
}
