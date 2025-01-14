package doctorhoai.learn.film_service.service.impl;

import doctorhoai.learn.film_service.dto.FilmDto;
import doctorhoai.learn.film_service.dto.request.FilmRequest;
import doctorhoai.learn.film_service.entity.Film;
import doctorhoai.learn.film_service.entity.Status;
import doctorhoai.learn.film_service.entity.TypeFilm;
import doctorhoai.learn.film_service.exception.ErrorException;
import doctorhoai.learn.film_service.exception.FilmNotFound;
import doctorhoai.learn.film_service.exception.TypeFilmNotFound;
import doctorhoai.learn.film_service.helper.MapperToDto;
import doctorhoai.learn.film_service.repository.FilmRepository;
import doctorhoai.learn.film_service.repository.TypeFilmRepository;
import doctorhoai.learn.film_service.service.inter.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final TypeFilmRepository typeFilmRepository;
    private final FilmRepository filmRepository;

    @Override
    @Transactional
    public FilmDto addFilm(FilmRequest film) {
        List<TypeFilm> listTypeFilm = new ArrayList<>();
        film.getTypeFilms().forEach(typeFilm -> {
            Optional<TypeFilm> tpl = typeFilmRepository.findById(typeFilm.getId());
            if( tpl.isEmpty()){
                throw new TypeFilmNotFound("Type film not found with id : " + typeFilm.getId());
            }
            listTypeFilm.add(tpl.get());
        });
        try{
            Film filmNew = Film.builder()
                    .name(film.getName())
                    .age(film.getAge())
                    .sub(film.getSub())
                    .description(film.getDescription())
                    .content(film.getContent())
                    .trailer(film.getTrailer())
                    .typeFilms(listTypeFilm)
                    .status(Status.ACTIVE)
                    .build();
           Film filmSaved = filmRepository.save(filmNew);
           return MapperToDto.FilmToDto(filmSaved);
       }catch (Exception e){
            log.error(e.getMessage());
            throw new FilmNotFound(e.getMessage());
        }
    }

    @Override
    @Transactional
    public FilmDto updateFilm(String id, FilmRequest film) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if( filmOptional.isEmpty()){
            throw new FilmNotFound("Film not found with id : " + id);
        }
        try{
            Film filmNew = filmOptional.get();
            filmNew.setName(film.getName());
            filmNew.setAge(film.getAge());
            filmNew.setSub(film.getSub());
            filmNew.setDescription(film.getDescription());
            filmNew.setContent(film.getContent());
            filmNew.setTrailer(film.getTrailer());
            filmNew.setTypeFilms(
                    film.getTypeFilms().stream().map(MapperToDto::DtoToTypeFilm).toList()
            );
            filmNew.setStatus(Status.valueOf(film.getStatus().toUpperCase()));
            Film fileSaved = filmRepository.save(filmNew);
            return MapperToDto.FilmToDto(fileSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteFilm(String id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if( filmOptional.isEmpty()){
            throw new FilmNotFound("Film not found with id : " + id);
        }
        try{
            Film filmNew = filmOptional.get();
            filmNew.setStatus(Status.DELETE);
            filmRepository.save(filmNew);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void activeFilm(String id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if( filmOptional.isEmpty()){
            throw new FilmNotFound("Film not found with id : " + id);
        }
        try{
            Film filmNew = filmOptional.get();
            filmNew.setStatus(Status.ACTIVE);
            filmRepository.save(filmNew);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public FilmDto getFilm(String id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if( filmOptional.isEmpty()){
            throw new FilmNotFound("Film not found with id : " + id);
        }
        try{
            return MapperToDto.FilmToDto(filmOptional.get());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<FilmDto> getFilms() {
        return filmRepository.findAll().stream().map(MapperToDto::FilmToDto).toList();
    }
}
