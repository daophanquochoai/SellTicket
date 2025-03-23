package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.entity.Contact;
import doctorhoai.learn.user_service.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("select c from Contact c where (c.name like concat('%',:q,'%') or c.content like concat('%',:q,'%')) and c.status = :status")
    Page<Contact> getContactByCustom(Pageable pageable, String q, Status status);
    @Query("select c from Contact c where c.name like concat('%',:q,'%') or c.content like concat('%',:q,'%') ")
    Page<Contact> getContactByCustom(Pageable pageable, String q);
}
