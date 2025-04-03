package doctorhoai.learn.film_service.service.impl;

import doctorhoai.learn.film_service.entity.RevenueFilm;
import doctorhoai.learn.film_service.repository.RevenueRepository;
import doctorhoai.learn.film_service.service.inter.RevenueFilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueFilmServiceImpl implements RevenueFilmService {

    private final RevenueRepository revenueRepository;

    @Override
    @Transactional
    public List<RevenueFilm> getRevenueByFilmId(String filmId) {
        return revenueRepository.revenueInFilmByFilmId(filmId);
    }

    @Override
    @Transactional
    public List<RevenueFilm> getRevenueInFilmAll(Integer month, Integer year) {
        return revenueRepository.revenueInFilm(month, year);
    }
}
