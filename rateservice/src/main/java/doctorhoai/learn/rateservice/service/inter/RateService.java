package doctorhoai.learn.rateservice.service.inter;

import doctorhoai.learn.rateservice.dto.RateFilmDto;
import doctorhoai.learn.rateservice.dto.request.RateFilmRequest;
import doctorhoai.learn.rateservice.dto.response.RateForFilm;

import java.util.List;

public interface RateService {
    RateFilmDto addRateFilm(String userid, String filmId, RateFilmRequest rate);
    void deleteRateFilm(String id);
    void activeRateFilm(String id);
    RateForFilm getRateByFilmId(String filmId, String limit, String page, String asc, String status, String q, String orderBy);
    List<RateFilmDto> getRateByCustom(String limit, String page, String asc, String status, String q, String orderBy);
}
