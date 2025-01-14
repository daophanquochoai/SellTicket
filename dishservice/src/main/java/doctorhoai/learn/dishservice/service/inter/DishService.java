package doctorhoai.learn.dishservice.service.inter;

import doctorhoai.learn.dishservice.dto.DishDto;
import doctorhoai.learn.dishservice.dto.request.DishRequest;

import java.util.List;

public interface DishService {
    DishDto getDishById(String id);
    List<DishDto> getAllDish();
    void deleteDish(String id);
    void activeDish(String id);
    DishDto addDish(DishRequest dishDto);
    DishDto updateDish(String id, DishRequest dishDto);
}
