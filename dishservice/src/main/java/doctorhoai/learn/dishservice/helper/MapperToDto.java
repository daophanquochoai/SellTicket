package doctorhoai.learn.dishservice.helper;

import doctorhoai.learn.dishservice.dto.DishDto;
import doctorhoai.learn.dishservice.dto.TypeDishDto;
import doctorhoai.learn.dishservice.entity.Dish;
import doctorhoai.learn.dishservice.entity.Status;
import doctorhoai.learn.dishservice.entity.TypeDish;

public class MapperToDto {
    public static TypeDishDto TypeDishToDto(TypeDish type) {
        if( type == null ) return null;
        return TypeDishDto.builder()
                .id(type.getId())
                .active(type.getActive().toString())
                .name(type.getName())
                .build();
    }

    public static TypeDish TypeDishDtoToType(TypeDishDto dto) {
        if( dto == null ) return null;
        return TypeDish.builder()
                .id(dto.getId())
                .name(dto.getName())
                .active(Status.valueOf(dto.getActive().toUpperCase()))
                .build();
    }

    public static Dish DtoToDish(DishDto dto){
        return Dish.builder()
                .id(dto.getId())
                .price(dto.getPrice())
                .active(Status.valueOf(dto.getActive().toUpperCase()))
                .name(dto.getName())
                .image(dto.getImage())
                .typeDish(MapperToDto.TypeDishDtoToType(dto.getTypeDish()))
                .build();
    }

    public static DishDto DishToDto(Dish dish){
        return DishDto.builder()
                .id(dish.getId())
                .price(dish.getPrice())
                .active(dish.getActive().toString())
                .name(dish.getName())
                .image(dish.getImage())
                .typeDish(MapperToDto.TypeDishToDto(dish.getTypeDish()))
                .build();
    }
}
