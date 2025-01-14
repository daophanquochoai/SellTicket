package doctorhoai.learn.dishservice.service.inter;

import doctorhoai.learn.dishservice.dto.TypeDishDto;

import java.util.List;

public interface TypeDishService {
    TypeDishDto getTypeDishById(String id);
    List<TypeDishDto> getAllTypeDish();
    void deleteTypeDish(String id);
    void activeTypeDish(String id);
    TypeDishDto addTypeDish(TypeDishDto typeDishDto);
    TypeDishDto updateTypeDish(String id,TypeDishDto typeDishDto);
}
