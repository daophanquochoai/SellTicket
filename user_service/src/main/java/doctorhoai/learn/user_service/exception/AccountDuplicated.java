package doctorhoai.learn.user_service.exception;

public class AccountDuplicated extends RuntimeException {
    public AccountDuplicated(String message) {
        super(message);
    }
}
