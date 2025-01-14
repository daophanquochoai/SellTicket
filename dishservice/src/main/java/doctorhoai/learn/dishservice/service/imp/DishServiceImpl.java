package doctorhoai.learn.dishservice.service.imp;

import doctorhoai.learn.dishservice.dto.DishDto;
import doctorhoai.learn.dishservice.dto.request.DishRequest;
import doctorhoai.learn.dishservice.entity.Dish;
import doctorhoai.learn.dishservice.entity.Status;
import doctorhoai.learn.dishservice.entity.TypeDish;
import doctorhoai.learn.dishservice.exception.DishNotFound;
import doctorhoai.learn.dishservice.exception.ErrorException;
import doctorhoai.learn.dishservice.exception.TypeDishNotFound;
import doctorhoai.learn.dishservice.helper.MapperToDto;
import doctorhoai.learn.dishservice.repository.DishRepository;
import doctorhoai.learn.dishservice.repository.TypeDishRepository;
import doctorhoai.learn.dishservice.service.inter.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final TypeDishRepository typeDishRepository;
    @Override
    public DishDto getDishById(String id) {
        Optional<Dish> dish = dishRepository.findById(id);
        if( dish.isEmpty() ){
            throw new DishNotFound("Dish not found with id : " + id);
        }
        return MapperToDto.DishToDto(dish.get());
    }

    @Override
    public List<DishDto> getAllDish() {
        return dishRepository.findAll().stream().map(MapperToDto::DishToDto).toList();
    }

    @Override
    public void deleteDish(String id) {
        Optional<Dish> dish = dishRepository.findById(id);
        if( dish.isEmpty() ){
            throw new DishNotFound("Dish not found with id : " + id);
        }
        Dish dishNew = dish.get();
        dishNew.setActive(Status.DELETE);
        try{
            dishRepository.save(dishNew);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public void activeDish(String id) {
        Optional<Dish> dish = dishRepository.findById(id);
        if( dish.isEmpty() ){
            throw new DishNotFound("Dish not found with id : " + id);
        }
        Dish dishNew = dish.get();
        dishNew.setActive(Status.ACTIVE);
        try{
            dishRepository.save(dishNew);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public DishDto addDish(DishRequest dishDto) {
        Dish dishNew = Dish.builder()
                .price(dishDto.getPrice())
                .active(Status.ACTIVE)
                .name(dishDto.getName())
                .image(dishDto.getImage())
                .build();
        Optional<TypeDish> typeDish = typeDishRepository.findById(dishDto.getTypeDishId());
        if( typeDish.isEmpty() ){
            throw new TypeDishNotFound("Type dish not found with id : " + dishDto.getTypeDishId());
        }
        dishNew.setTypeDish(typeDish.get());
        try{
            Dish dishSaved = dishRepository.save(dishNew);
            return MapperToDto.DishToDto(dishSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public DishDto updateDish(String id, DishRequest dishDto) {
        Optional<Dish> dish = dishRepository.findById(id);
        if( dish.isEmpty() ){
            throw new DishNotFound("Dish not found with id : " + id);
        }
        Dish dishNew = dish.get();
        dishNew.setName(dishDto.getName());
        dishNew.setImage(dishDto.getImage());
        dishNew.setPrice(dishDto.getPrice());
        dishNew.setActive(Status.ACTIVE);
        if( !dishDto.getTypeDishId().equals(dishNew.getTypeDish().getId())){
            Optional<TypeDish> typeDish = typeDishRepository.findById(dishDto.getTypeDishId());
            if( typeDish.isEmpty() ){
                throw new TypeDishNotFound("Type dish not found with id : " + dishDto.getTypeDishId());
            }
            dishNew.setTypeDish(typeDish.get());
        }
        try{
            Dish dishSaved = dishRepository.save(dishNew);
            return MapperToDto.DishToDto(dishSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }


}
