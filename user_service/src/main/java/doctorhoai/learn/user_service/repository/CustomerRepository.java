package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.entity.Customer;
import doctorhoai.learn.user_service.entity.Provider;
import doctorhoai.learn.user_service.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmailOrPhoneNumberAndProvider(String email, String phoneNumber, Provider provider);
    Optional<Customer> findByEmailOrPhoneNumberAndProviderNot(String email, String phoneNumber, Provider provider);
    Optional<Customer> findByEmailAndProvider(String email, Provider provider);
    @Query("select c from Customer c where c.status = :status and ( c.email like concat('%', :q, '%') or c.name like concat('%', :q, '%') or c.phoneNumber like concat('%', :q, '%'))")
    Page<Customer> findAllCustomer(Pageable pageable, String q, Status status);
    @Query("select c from Customer c where c.email like concat('%', :q, '%') or c.name like concat('%', :q, '%') or c.phoneNumber like concat('%', :q, '%')")
    Page<Customer> findAllCustomer(Pageable pageable, String q);
    Optional<Customer> getCustomerByAccount_UserName(String username);
    @Query("select c from Customer c join fetch c.account where c.id = :id")
    Optional<Customer> getCustomerById(String id);

}
