package doctorhoai.learn.film_service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.film_service.dto.FilmDto;
import doctorhoai.learn.film_service.dto.FilmShowDto;
import doctorhoai.learn.film_service.dto.SubDto;
import doctorhoai.learn.film_service.dto.SubFilmDto;
import doctorhoai.learn.film_service.dto.request.SubFilmRequest;
import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.entity.Film;
import doctorhoai.learn.film_service.entity.Sub;
import doctorhoai.learn.film_service.entity.SubFilm;
import doctorhoai.learn.film_service.exception.ErrorException;
import doctorhoai.learn.film_service.exception.SubFilmNotFound;
import doctorhoai.learn.film_service.facade.FilmShowAsyncFacade;
import doctorhoai.learn.film_service.repository.FilmRepository;
import doctorhoai.learn.film_service.repository.SubFilmRepository;
import doctorhoai.learn.film_service.repository.SubRepository;
import doctorhoai.learn.film_service.service.feignclient.feign.FilmShowFeign;
import doctorhoai.learn.film_service.service.inter.SubFilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubFilmServiceImpl implements SubFilmService {

    private final SubFilmRepository subFilmRepository;
    private final SubRepository subRepository;
    private final FilmRepository filmRepository;
    private final FilmShowFeign filmShowFeign;
    private final FilmShowAsyncFacade filmShowAsyncFacade;

    @Override
    public SubFilmDto getSubFilmById(String id) {
        Optional<SubFilm> subFilm = subFilmRepository.findById(id);
        if ( subFilm.isEmpty() ){
            throw new SubFilmNotFound("Sub film not found");
        }
        SubFilm subF = subFilm.get();
        Film film = subF.getFilmId();
        FilmDto filmDto = FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .age(film.getAge())
                .description(film.getDescription())
                .content(film.getContent())
                .trailer(film.getTrailer())
                .build();
        SubDto subDto = SubDto.builder()
                .id(subF.getSubId().getId())
                .name(subF.getSubId().getSub())
                .build();
        SubFilmDto subFDto = SubFilmDto.builder()
                .id(subF.getId())
                .filmDto(filmDto)
                .subDto(subDto)
                .build();
        return subFDto;
    }

    @Override
    public SubFilmDto getSubFilmByFilmIdAndSubId(String filmId, String subId) {
        Optional<SubFilm> subFilm = subFilmRepository.getSubFilmsByFilmId_IdAndSubId_Id(filmId, subId);
        if ( subFilm.isEmpty() ){
            throw new SubFilmNotFound("Sub film not found");
        }
        Film film = subFilm.get().getFilmId();
        FilmDto filmDto = FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .age(film.getAge())
                .description(film.getDescription())
                .content(film.getContent())
                .trailer(film.getTrailer())
                .build();
        SubFilmDto subFDto = SubFilmDto.builder()
                .id(subFilm.get().getId())
                .filmDto(filmDto)
                .build();
        return subFDto;
    }

    @Override
    public Page<SubFilmDto> getSubFilmBySubId(String page, String limit, String asc, String subId) {
        Page<SubFilm> list;
        Pageable pageable;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit));
        }
        list = subFilmRepository.getSubFilmsBySubId_Id(pageable, subId);
        return list.map( sf -> {
            Film film = sf.getFilmId();
            FilmDto filmDto = FilmDto.builder()
                    .id(film.getId())
                    .name(film.getName())
                    .age(film.getAge())
                    .description(film.getDescription())
                    .content(film.getContent())
                    .trailer(film.getTrailer())
                    .build();
            SubFilmDto subFDto = SubFilmDto.builder()
                    .id(sf.getId())
                    .filmDto(filmDto)
                    .build();
            return subFDto;
        });
    }

    @Override
    public SubFilmDto addSubFilm(SubFilmRequest subFilmRequest) {
        Optional<Sub> subOptional = subRepository.findById(subFilmRequest.getSubId());
        Optional<Film> filmOptional = filmRepository.findById(subFilmRequest.getFilmId());
        if( subOptional.isEmpty() || filmOptional.isEmpty() ){
            throw new SubFilmNotFound("Sub film or Film not found");
        }
        SubFilm subFilm = SubFilm.builder()
                .subId(subOptional.get())
                .filmId(filmOptional.get())
                .build();
        try{
            SubFilm subFilmSaved = subFilmRepository.save(subFilm);
            Film film = subFilmSaved.getFilmId();
            FilmDto filmDto = FilmDto.builder()
                    .id(film.getId())
                    .name(film.getName())
                    .age(film.getAge())
                    .description(film.getDescription())
                    .content(film.getContent())
                    .trailer(film.getTrailer())
                    .build();
            SubFilmDto subFDto = SubFilmDto.builder()
                    .id(subFilmSaved.getId())
                    .filmDto(filmDto)
                    .build();
            return subFDto;
        }catch (Exception e){
            throw new ErrorException("Save Error : " + e.getMessage() );
        }
    }

    @Override
    public boolean deleteSubFilm(String filmId, String subId) {
        Optional<SubFilm> subFilm = subFilmRepository.getSubFilmsByFilmId_IdAndSubId_Id(filmId, subId);
        if( subFilm.isEmpty() ){
            throw new SubFilmNotFound("Sub film not found");
        }
        //async
        CompletableFuture<ResponseEntity<Response>> responseAsync = filmShowAsyncFacade.getFilmShowBySubFilm(subFilm.get().getId());
        //wait
        CompletableFuture.allOf(responseAsync).join();
        ResponseEntity<Response> response = responseAsync.join();
        if( response.getStatusCode() != HttpStatus.OK){
            throw new ErrorException("Server Down!");
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        List<FilmShowDto> filmDtos = objectMapper.convertValue(response.getBody().getData(), new TypeReference<List<FilmShowDto>>(){});
        if( filmDtos.size() > 0 ){
            return false;
        }
        try{
            subFilmRepository.delete(subFilm.get());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SubFilmDto> getAll() {
        List<SubFilm> list = subFilmRepository.findAll();
        return list.stream().map(item -> {
            Film film = item.getFilmId();
            FilmDto filmDto = FilmDto.builder()
                    .id(film.getId())
                    .name(film.getName())
                    .age(film.getAge())
                    .description(film.getDescription())
                    .content(film.getContent())
                    .trailer(film.getTrailer())
                    .build();
            SubDto subDto = SubDto.builder()
                    .id(item.getSubId().getId())
                    .name(item.getSubId().getSub())
                    .build();
            SubFilmDto subFDto = SubFilmDto.builder()
                    .id(item.getId())
                    .filmDto(filmDto)
                    .subDto(subDto)
                    .build();
            return subFDto;
        }).toList();
    }
}
