package doctorhoai.learn.film_service.service.impl;

import doctorhoai.learn.film_service.dto.TypeFilmDto;
import doctorhoai.learn.film_service.entity.Status;
import doctorhoai.learn.film_service.entity.TypeFilm;
import doctorhoai.learn.film_service.exception.ErrorException;
import doctorhoai.learn.film_service.exception.TypeFilmNotFound;
import doctorhoai.learn.film_service.helper.MapperToDto;
import doctorhoai.learn.film_service.repository.TypeFilmRepository;
import doctorhoai.learn.film_service.service.inter.TypeFilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeFilmServiceImpl implements TypeFilmService {
    private final TypeFilmRepository typeFilmRepository;

    @Override
    @Transactional
    public TypeFilmDto addTypeFilm(TypeFilmDto typeFilmDto) {
        try{
            TypeFilm typeFilm = MapperToDto.DtoToTypeFilm(typeFilmDto);
            return MapperToDto.TypeFilmToDto(typeFilmRepository.save(typeFilm));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public TypeFilmDto updateTypeFilm(String id, TypeFilmDto typeFilmDto) {
        Optional<TypeFilm> typeFilm = typeFilmRepository.findById(id);
        if( typeFilm.isEmpty()){
            throw new TypeFilmNotFound("Type film not found with id : " + id);
        }
        TypeFilm tp = typeFilm.get();
        tp.setName(typeFilmDto.getName());
        tp.setActive(Status.valueOf(typeFilmDto.getActive()));
        try{
            return MapperToDto.TypeFilmToDto(typeFilmRepository.save(tp));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public TypeFilmDto getTypeFilmById(String id) {
        Optional<TypeFilm> typeFilm = typeFilmRepository.findById(id);
        if( typeFilm.isEmpty()){
            throw new TypeFilmNotFound("Type film not found with id : " + id);
        }
        try{
            return MapperToDto.TypeFilmToDto(typeFilm.get());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<TypeFilmDto> getAllTypeFilm() {
        return typeFilmRepository.findAll().stream().map(MapperToDto::TypeFilmToDto).toList();
    }

    @Override
    @Transactional
    public void deleteTypeFilm(String id) {
        Optional<TypeFilm> typeFilm = typeFilmRepository.findById(id);
        if( typeFilm.isEmpty()){
            throw new TypeFilmNotFound("Type film not found with id : " + id);
        }
        typeFilm.get().setActive(Status.DELETE);
        try{
            typeFilmRepository.save(typeFilm.get());
        }catch (Exception ex ){
            log.error(ex.getMessage());
            throw new ErrorException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void activeTypeFilm(String id) {
        Optional<TypeFilm> typeFilm = typeFilmRepository.findById(id);
        if( typeFilm.isEmpty()){
            throw new TypeFilmNotFound("Type film not found with id : " + id);
        }
        typeFilm.get().setActive(Status.ACTIVE);
        try{
            typeFilmRepository.save(typeFilm.get());
        }catch (Exception ex ){
            log.error(ex.getMessage());
            throw new ErrorException(ex.getMessage());
        }
    }
}
