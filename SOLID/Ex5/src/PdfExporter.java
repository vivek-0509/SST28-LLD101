import java.nio.charset.StandardCharsets;

public class PdfExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        if (req.body.length() > 20) {
            return ExportResult.fail("PDF cannot handle content > 20 chars");
        }
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
