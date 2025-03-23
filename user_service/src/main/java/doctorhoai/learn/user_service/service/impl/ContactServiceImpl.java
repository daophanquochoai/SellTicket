package doctorhoai.learn.user_service.service.impl;

import doctorhoai.learn.user_service.dto.ContactDto;
import doctorhoai.learn.user_service.entity.Contact;
import doctorhoai.learn.user_service.entity.Status;
import doctorhoai.learn.user_service.exception.ContactNotFound;
import doctorhoai.learn.user_service.exception.ErrorException;
import doctorhoai.learn.user_service.helper.MapperToDto;
import doctorhoai.learn.user_service.repository.ContactRepository;
import doctorhoai.learn.user_service.service.inter.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public ContactDto addContact(ContactDto contactDto) {
        Contact contact = MapperToDto.DtoToContact(contactDto);
        try{
            Contact contactSaved = contactRepository.save(contact);
            return MapperToDto.ContactToDto(contactSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException("Contact : 32 : " + e.getMessage());
        }
    }

    @Override
    public ContactDto checkContact(Integer id) {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (contactOptional.isEmpty()) {
            throw new ContactNotFound("Contact Not Found With ID: " + id);
        }
        try{
            contactOptional.get().setStatus(Status.DELETE);
            Contact contactSaved = contactRepository.save(contactOptional.get());
            return MapperToDto.ContactToDto(contactSaved);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException("Contact : 41 : " + e.getMessage());
        }
    }

    @Override
    public List<ContactDto> getAllContacts() {
        List<Contact> ls = contactRepository.findAll();
        return contactRepository.findAll().stream().map(MapperToDto::ContactToDto).toList();
    }

    @Override
    public Page<ContactDto> getContactByCustom(String page, String limit, String asc, String q, String orderBy, String status) {
        Page<Contact> list;
        Pageable pageable;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy).descending());
        }
        if( status.equals("none")){
            list = contactRepository.getContactByCustom(pageable, q);
        }else{
            list = contactRepository.getContactByCustom(pageable,q,Status.valueOf(status.toUpperCase()));
        }
        return list.map(MapperToDto::ContactToDto);
    }
}
