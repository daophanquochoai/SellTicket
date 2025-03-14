package doctorhoai.learn.paymentservice.service.inter;

import com.stripe.model.Charge;
import com.stripe.model.Customer;

public interface StripeService {
     Customer createCustomer(String token,String email) throws Exception;
     Customer getCustomer(String id ) throws Exception;
     Charge chargeNewCard(String token, double amount) throws Exception;
     Charge chargeCustomerCard(String customerId, int amount) throws Exception;
}
