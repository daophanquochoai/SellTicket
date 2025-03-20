package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.dto.SubDto;
import doctorhoai.learn.film_service.dto.request.SubRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubService {
    List<SubDto> getSubs();
    Page<SubDto> getSubsByCustom(String page, String limit, String asc, String orderBy, String q);
    SubDto addSub(SubRequest sub);
    SubDto updateSub(String id,SubRequest sub);
    Boolean deleteSub(String id);
}
