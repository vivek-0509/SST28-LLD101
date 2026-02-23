public class SendResult {
    public final boolean success;
    public final String error;

    private SendResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public static SendResult ok() { return new SendResult(true, null); }
    public static SendResult fail(String error) { return new SendResult(false, error); }
}
