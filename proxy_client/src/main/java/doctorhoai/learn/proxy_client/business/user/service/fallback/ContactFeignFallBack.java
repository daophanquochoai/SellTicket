package doctorhoai.learn.proxy_client.business.user.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.user.model.ContactDto;
import doctorhoai.learn.proxy_client.business.user.service.ContactFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactFeignFallBack implements FallbackFactory<ContactFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public ContactFeign create(Throwable cause) {
        return new ContactFeign() {
            @Override
            public ResponseEntity<Response> addContact(ContactDto contactDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> checkContact(Integer id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllContact() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getContactByCustom(String limit, String page, String q, String asc, String status, String orderBy) {
                return functionCommon.process(cause);
            }
        };
    }
}
