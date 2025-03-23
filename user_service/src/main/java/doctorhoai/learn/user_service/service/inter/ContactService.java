package doctorhoai.learn.user_service.service.inter;

import doctorhoai.learn.user_service.dto.ContactDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    ContactDto addContact(ContactDto contactDto);
    ContactDto checkContact(Integer id);
    List<ContactDto> getAllContacts();
    Page<ContactDto> getContactByCustom(String page, String limit, String asc, String q, String orderBy, String status);
}
