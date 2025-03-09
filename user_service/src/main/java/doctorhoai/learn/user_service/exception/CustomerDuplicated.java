package doctorhoai.learn.user_service.exception;

public class CustomerDuplicated extends RuntimeException {
    public CustomerDuplicated(String message) {
        super(message);
    }
}
