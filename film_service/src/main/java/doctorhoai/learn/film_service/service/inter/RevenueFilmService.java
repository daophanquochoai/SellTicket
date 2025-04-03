package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.entity.RevenueFilm;

import java.util.List;

public interface RevenueFilmService {
    List<RevenueFilm> getRevenueByFilmId(String filmId);
    List<RevenueFilm> getRevenueInFilmAll(Integer month, Integer year);
}
