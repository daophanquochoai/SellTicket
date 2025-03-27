package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.entity.RevenueFilm;

import java.util.List;

public interface RevenueFilmService {
    List<RevenueFilm> getRevenueInFilm();
    List<RevenueFilm> getRevenueInFilmAll();
}
