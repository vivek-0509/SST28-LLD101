public class ExportResult {
    public final String contentType;
    public final byte[] bytes;
    public final String error;

    public ExportResult(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
        this.error = null;
    }

    private ExportResult(String error) {
        this.contentType = null;
        this.bytes = new byte[0];
        this.error = error;
    }

    public static ExportResult fail(String message) {
        return new ExportResult(message);
    }

    public boolean isSuccess() {
        return error == null;
    }
}
