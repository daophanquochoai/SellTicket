package doctorhoai.learn.user_service.exception;

public class DuplicateEmployee extends RuntimeException {
    public DuplicateEmployee(String message) {
        super(message);
    }
}
