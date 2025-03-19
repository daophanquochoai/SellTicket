package doctorhoai.learn.film_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.film_service.dto.FilmDto;
import doctorhoai.learn.film_service.dto.FilmShowDto;
import doctorhoai.learn.film_service.dto.SubDto;
import doctorhoai.learn.film_service.dto.request.FilmRequest;
import doctorhoai.learn.film_service.dto.response.Response;
import doctorhoai.learn.film_service.entity.*;
import doctorhoai.learn.film_service.exception.ErrorException;
import doctorhoai.learn.film_service.exception.FilmNotFound;
import doctorhoai.learn.film_service.exception.SubNotFound;
import doctorhoai.learn.film_service.exception.TypeFilmNotFound;
import doctorhoai.learn.film_service.helper.MapperToDto;
import doctorhoai.learn.film_service.repository.FilmRepository;
import doctorhoai.learn.film_service.repository.SubFilmRepository;
import doctorhoai.learn.film_service.repository.SubRepository;
import doctorhoai.learn.film_service.repository.TypeFilmRepository;
import doctorhoai.learn.film_service.service.feignclient.feign.FilmShowFeign;
import doctorhoai.learn.film_service.service.inter.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final TypeFilmRepository typeFilmRepository;
    private final FilmRepository filmRepository;
    private final SubRepository subRepository;
    private final FilmShowFeign filmShowFeign;
    private final SubFilmRepository subFilmRepository;

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
                    .image(film.getImage())
                    .duration(film.getDuration())
                    .nation(film.getNation())
                    .description(film.getDescription())
                    .content(film.getContent())
                    .trailer(film.getTrailer())
                    .typeFilms(listTypeFilm)
                    .status(Status.ACTIVE)
                    .build();
            List<SubFilm> subFilms = new ArrayList<>();
            film.getSub().forEach( item -> {
                Optional<Sub> subOptional = subRepository.findById(item.getId());
                if( subOptional.isEmpty()){
                    throw new SubNotFound("Sub not found with id : " + item.getId());
                }
                subFilms.add(SubFilm.builder().subId(subOptional.get()).filmId(filmNew).build());
            });
            filmNew.setSubFilms(subFilms);
           Film filmSaved = filmRepository.save(filmNew);
           FilmDto filmDto = MapperToDto.FilmToDto(filmSaved);
           List<SubDto> subDtos= new ArrayList<>();
           filmSaved.getSubFilms().forEach( item -> {
               SubDto temp = new SubDto(item.getSubId().getId(), item.getSubId().getSub());
               subDtos.add(temp);
           });
           filmDto.setSub(subDtos);
           return filmDto;
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
            filmNew.setImage(film.getImage());
            filmNew.setAge(film.getAge());
            filmNew.setNation(film.getNation());
            filmNew.setDuration(film.getDuration());
            filmNew.setDescription(film.getDescription());
            filmNew.setContent(film.getContent());
            filmNew.setTrailer(film.getTrailer());
            filmNew.setTypeFilms(
                    film.getTypeFilms().stream().map(MapperToDto::DtoToTypeFilm).collect(Collectors.toList())
            );

            List<SubFilm> subFilms = new ArrayList<>();
            film.getSub().stream().forEach(item -> {
                Optional<SubFilm> temp = filmNew.getSubFilms().stream().filter(i ->i.getSubId().getId().equals(item.getId())).findFirst();
                if(temp.isEmpty()){
                    Optional<Sub> subOptional = subRepository.findById(item.getId());
                    if( subOptional.isEmpty()){
                        throw new SubNotFound("Sub not found with id : " + item.getId());
                    }
                    subFilms.add(SubFilm.builder()
                            .subId(subOptional.get())
                            .filmId(filmNew)
                            .build());
                }
                else{
                    subFilms.add(temp.get());
                }
            });

            filmNew.setSubFilms(subFilms);
            filmNew.setStatus(Status.valueOf(film.getStatus().toUpperCase()));
            Film filmSaved = filmRepository.save(filmNew);
            subFilmRepository.deleteBySubFilm();
            FilmDto filmDto = MapperToDto.FilmToDto(filmSaved);
            List<SubDto> subDtos= new ArrayList<>();
            filmSaved.getSubFilms().forEach( item -> {
                SubDto temp = new SubDto(item.getSubId().getId(), item.getSubId().getSub());
                subDtos.add(temp);
            });
            filmDto.setSub(subDtos);
            return filmDto;
        }catch (Exception e){
            log.error("Loi : " + e);
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
    @Transactional
    public FilmDto getFilm(String id) {
        Optional<Film> filmOptional = filmRepository.findFilmBySub(id);
        if( filmOptional.isEmpty()){
            throw new FilmNotFound("Film not found with id : " + id);
        }
        try{
            FilmDto filmDto = MapperToDto.FilmToDto(filmOptional.get());
            List<SubDto> subDtos= new ArrayList<>();
            filmOptional.get().getSubFilms().forEach( item -> {
                SubDto temp = new SubDto(item.getSubId().getId(), item.getSubId().getSub());
                subDtos.add(temp);
            });
            filmDto.setSub(subDtos);
            return filmDto;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<FilmDto> getFilms() {
        return filmRepository.getFilmByOrther().stream().map(
                film -> {
                    FilmDto filmDto = MapperToDto.FilmToDto(film);
                    List<SubDto> subDtos= new ArrayList<>();
                    film.getSubFilms().forEach( item -> {
                        SubDto temp = new SubDto(item.getSubId().getId(), item.getSubId().getSub());
                        subDtos.add(temp);
                    });
                    filmDto.setSub(subDtos);
                    return filmDto;
                }
        ).toList();
    }

    @Override
    public Page<FilmDto> getFilmByCustom(String limit, String page, String asc, String orderBy, String q, String active) {
        Page<Film> list;
        Pageable pageable;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy).descending());
        }
        if( active.equals("none")){
            list = filmRepository.getFilmByCustom(pageable,q);
        }else{
            list = filmRepository.getFilmByCustom(pageable,q, Status.valueOf(active));
        }
        return list.map(
                film -> {
                    FilmDto filmDto = MapperToDto.FilmToDto(film);
                    List<SubDto> subDtos= new ArrayList<>();
                    film.getSubFilms().forEach( item -> {
                        SubDto temp = new SubDto(item.getSubId().getId(), item.getSubId().getSub());
                        subDtos.add(temp);
                    });
                    filmDto.setSub(subDtos);
                    return filmDto;
                }
        );
    }

    @Override
    public List<FilmDto> getFilmBySearch(String branchId, LocalDate time) {
        try{
            ResponseEntity<Response> response = filmShowFeign.getFilmShowTimeByParam(branchId,time);
            if( response.getStatusCode() != HttpStatusCode.valueOf(200)){
                throw new ErrorException("Show time service down");
            }
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(response.getBody().getData());
            List<FilmShowDto> list = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, FilmShowDto.class));
            List<String> idFilm = list.stream().map(FilmShowDto::getSubFilmId).toList();
            List<Film> films = filmRepository.getFilmBySubFilmsIdIn(idFilm);
            List<FilmDto> returnList = new ArrayList<>();
            films.forEach( item -> {
                FilmDto film = FilmDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .age(item.getAge())
                        .image(item.getImage())
                        .duration(item.getDuration())
                        .nation(item.getNation())
                        .description(item.getDescription())
                        .content(item.getContent())
                        .trailer(item.getTrailer())
                        .status(item.getStatus().toString())
                        .build();
                List<SubDto> subs = new ArrayList<>();
                item.getSubFilms().forEach( sub -> {
                    SubDto temp = new SubDto(sub.getSubId().getId(), sub.getSubId().getSub());
                    List<FilmShowDto> filmShowTemp = list.stream().filter(filmS -> {
                        if( filmS.getSubFilmId().equals(sub.getId())){
                            return true;
                        }
                        return false;
                    }).toList();
                    temp.setFilmShowDtos(filmShowTemp);
                    subs.add(temp);
                });
                film.setSub(subs);
                returnList.add(film);
            });
            return returnList;
        }catch (Exception e){
            throw new ErrorException(e.getMessage());
        }

    }

    @Override
    public List<FilmDto> getFilmByStatus(String status) {
        List<Film> list = filmRepository.getFilmByStatus(Status.valueOf(status));
        return list.stream().map(
                film -> {
                    FilmDto filmDto = MapperToDto.FilmToDto(film);
                    List<SubDto> subDtos= new ArrayList<>();
                    film.getSubFilms().forEach( item -> {
                        SubDto temp = new SubDto(item.getSubId().getId(), item.getSubId().getSub());
                        subDtos.add(temp);
                    });
                    filmDto.setSub(subDtos);
                    return filmDto;
                }
        ).toList();
    }
}
