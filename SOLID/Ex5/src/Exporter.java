public abstract class Exporter {

    public final ExportResult export(ExportRequest req) {
        if (req == null || req.title == null) {
            return ExportResult.fail("request and title must not be null");
        }
        String body = (req.body == null) ? "" : req.body;
        return doExport(new ExportRequest(req.title, body));
    }

    protected abstract ExportResult doExport(ExportRequest req);
}
