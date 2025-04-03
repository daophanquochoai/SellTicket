package doctorhoai.learn.film_service.facade;

import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.service.feignclient.feign.FilmShowFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmShowAsyncFacade {

    private final FilmShowFeign filmShowFeign;

    public CompletableFuture<ResponseEntity<Response>> getFilmShowTimeByParam(String branchId, LocalDate time){
        return CompletableFuture.supplyAsync(() -> filmShowFeign.getFilmShowTimeByParam(branchId, time));
    }

    public CompletableFuture<ResponseEntity<Response>> getFilmShowBySubFilm(String subFilmId){
        return CompletableFuture.supplyAsync(() -> filmShowFeign.getFilmShowBySubFilm(subFilmId));
    }
}
