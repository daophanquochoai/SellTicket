package doctorhoai.learn.rateservice.service.inter;

import doctorhoai.learn.rateservice.dto.RateFilmDto;
import doctorhoai.learn.rateservice.dto.request.RateFilmRequest;
import doctorhoai.learn.rateservice.dto.response.PageObject;

public interface RateService {
    RateFilmDto addRateFilm(String userid, String filmId, RateFilmRequest rate);
    void deleteRateFilm(String id);
    void activeRateFilm(String id);
    PageObject getRateByFilmId(String filmId, String limit, String page, String asc, String status, String q, String orderBy);
    PageObject getRateByCustom(String limit, String page, String asc, String status, String q, String orderBy);
    Boolean checkComment(String filmId, String userId);
}
