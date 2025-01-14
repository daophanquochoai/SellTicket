package doctorhoai.learn.dishservice.service.imp;

import doctorhoai.learn.dishservice.dto.TypeDishDto;
import doctorhoai.learn.dishservice.entity.Status;
import doctorhoai.learn.dishservice.entity.TypeDish;
import doctorhoai.learn.dishservice.exception.ErrorException;
import doctorhoai.learn.dishservice.exception.TypeDishNotFound;
import doctorhoai.learn.dishservice.helper.MapperToDto;
import doctorhoai.learn.dishservice.repository.TypeDishRepository;
import doctorhoai.learn.dishservice.service.inter.TypeDishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeDishServiceImpl implements TypeDishService {

    private final TypeDishRepository typeDishRepository;

    @Override
    public TypeDishDto getTypeDishById(String id) {
        Optional<TypeDish> typeDishOptional = typeDishRepository.findById(id);
        if( typeDishOptional.isEmpty()){
            throw new TypeDishNotFound("Type dish not found with id : " + id);
        }
        return MapperToDto.TypeDishToDto(typeDishOptional.get());
    }

    @Override
    public List<TypeDishDto> getAllTypeDish() {
        return typeDishRepository.findAll().stream().map(MapperToDto::TypeDishToDto).toList();
    }

    @Override
    public void deleteTypeDish(String id) {
        Optional<TypeDish> typeDishOptional = typeDishRepository.findById(id);
        if( typeDishOptional.isEmpty()){
            throw new TypeDishNotFound("Type dish not found with id : " + id);
        }
        TypeDish typeDish = typeDishOptional.get();
        typeDish.setActive(Status.DELETE);
        try{
            typeDishRepository.save(typeDish);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public void activeTypeDish(String id) {
        Optional<TypeDish> typeDishOptional = typeDishRepository.findById(id);
        if( typeDishOptional.isEmpty()){
            throw new TypeDishNotFound("Type dish not found with id : " + id);
        }
        TypeDish typeDish = typeDishOptional.get();
        typeDish.setActive(Status.ACTIVE);
        try{
            typeDishRepository.save(typeDish);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public TypeDishDto addTypeDish(TypeDishDto typeDishDto) {
        TypeDish typeDish = TypeDish.builder()
                .name(typeDishDto.getName())
                .active(Status.ACTIVE)
                .build();
        try{
            TypeDish typeDishSaved = typeDishRepository.save(typeDish);
            return MapperToDto.TypeDishToDto(typeDishSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public TypeDishDto updateTypeDish(String id, TypeDishDto typeDishDto) {
        Optional<TypeDish> typeDishOptional = typeDishRepository.findById(id);
        if( typeDishOptional.isEmpty()){
            throw new TypeDishNotFound("Type dish not found with id : " + id);
        }
        TypeDish typeDish = typeDishOptional.get();
        typeDish.setName(typeDishDto.getName());
        typeDish.setActive(Status.valueOf(typeDishDto.getActive().toUpperCase()));
        try{
            TypeDish typeDishUpdated = typeDishRepository.save(typeDish);
            return MapperToDto.TypeDishToDto(typeDishUpdated);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
}
