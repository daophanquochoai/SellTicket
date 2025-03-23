package doctorhoai.learn.film_service.service.impl;

import doctorhoai.learn.film_service.dto.SliderDto;
import doctorhoai.learn.film_service.entity.Slider;
import doctorhoai.learn.film_service.exception.ErrorException;
import doctorhoai.learn.film_service.helper.MapperToDto;
import doctorhoai.learn.film_service.repository.SliderRepository;
import doctorhoai.learn.film_service.service.inter.SliderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SliderServiceImpl implements SliderService {

    private final SliderRepository sliderRepository;

    @Override
    public List<SliderDto> changeSlider(List<SliderDto> list) {
        try{
            List<SliderDto> result = list.stream().filter(i -> i.getId() == 0).toList();
            List<Slider> convert = result.stream().map(MapperToDto::DtoToSlider).toList();
            List<Slider> listSaved = sliderRepository.saveAll(convert);
            return listSaved.stream().map(MapperToDto::SliderToDto).toList();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException("Slider : 31 : " + e.getMessage());
        }
    }

    @Override
    public List<SliderDto> getAll() {
        List<Slider> list = sliderRepository.findAll();
        return list.stream().map(MapperToDto::SliderToDto).toList();
    }

    @Override
    public SliderDto saveSlider(SliderDto slider) {
        Slider sli = MapperToDto.DtoToSlider(slider);
        try{
            Slider sliderSaved = sliderRepository.save(sli);
            return MapperToDto.SliderToDto(sli);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException("Slider : 49 : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void removeSlider(Integer id) {
        Optional<Slider> slider = sliderRepository.findById(id);
        if( slider.isEmpty()){
            throw new ErrorException("Slider : 58 : Slider not found with id : " + id);
        }
        try{
            sliderRepository.deleteById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException("Slider : 64 : " + e.getMessage());
        }
    }
}
