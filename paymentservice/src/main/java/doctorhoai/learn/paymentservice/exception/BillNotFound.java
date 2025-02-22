package doctorhoai.learn.paymentservice.exception;

public class BillNotFound extends RuntimeException {
    public BillNotFound(String message) {
        super(message);
    }
}
