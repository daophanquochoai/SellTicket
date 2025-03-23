package doctorhoai.learn.film_service.service.inter;

import doctorhoai.learn.film_service.dto.SliderDto;

import java.util.List;

public interface SliderService {
    List<SliderDto> changeSlider(List<SliderDto> list);
    List<SliderDto> getAll();
    SliderDto saveSlider(SliderDto slider);
    void removeSlider(Integer id);
}
