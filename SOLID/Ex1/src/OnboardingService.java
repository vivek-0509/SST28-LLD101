import java.util.*;

public class OnboardingService {
    private final InputParser parser;
    private final StudentValidator validator;
    private final StudentRepository repo;
    private final OnboardingPrinter printer;

    public OnboardingService(InputParser parser, StudentValidator validator, StudentRepository repo, OnboardingPrinter printer) {
        this.parser = parser;
        this.validator = validator;
        this.repo = repo;
        this.printer = printer;
    }

    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        Map<String, String> fields = parser.parse(raw);

        List<String> errors = validator.validate(fields);
        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repo.count());
        StudentRecord rec = new StudentRecord(id, fields.get("name"), fields.get("email"), fields.get("phone"), fields.get("program"));
        repo.save(rec);
        printer.printConfirmation(id, repo.count(), rec);
    }
}
