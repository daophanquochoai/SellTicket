package doctorhoai.learn.film_service.service.impl;

import doctorhoai.learn.film_service.dto.SubDto;
import doctorhoai.learn.film_service.entity.Sub;
import doctorhoai.learn.film_service.repository.SubRepository;
import doctorhoai.learn.film_service.service.inter.SubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubServiceImpl implements SubService {

    private final SubRepository subRepository;

    @Override
    public List<SubDto> getSubs() {
        List<Sub> subs = subRepository.findAll();
        return subs.stream().map( item -> {
            return SubDto.builder()
                    .id(item.getId())
                    .name(item.getSub())
                    .build();
        }).toList();
    }
}
