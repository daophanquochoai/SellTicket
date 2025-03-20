package doctorhoai.learn.film_service.service.impl;

import doctorhoai.learn.film_service.dto.SubDto;
import doctorhoai.learn.film_service.dto.request.SubRequest;
import doctorhoai.learn.film_service.entity.Sub;
import doctorhoai.learn.film_service.exception.ErrorException;
import doctorhoai.learn.film_service.exception.SubNotFound;
import doctorhoai.learn.film_service.helper.MapperToDto;
import doctorhoai.learn.film_service.repository.SubRepository;
import doctorhoai.learn.film_service.service.inter.SubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<SubDto> getSubsByCustom(String page, String limit, String asc, String orderBy, String q) {
        Page<Sub> list;
        Pageable pageable;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy).descending());
        }
        list = subRepository.getSubByCustom(pageable, q);
        return list.map(MapperToDto::SubtoDto);
    }

    @Override
    @Transactional
    public SubDto addSub(SubRequest subRequest) {
        Sub sub = Sub.builder().sub(subRequest.getName()).build();
        try{
            Sub subSaved = subRepository.save(sub);
            return MapperToDto.SubtoDto(subSaved);
        }catch (Exception e){
            e.printStackTrace();
            throw new ErrorException("Save Error : " + e.getMessage());
        }
    }

    @Override
    public SubDto updateSub(String id,SubRequest subRequest) {
        Optional<Sub> sub = subRepository.findById(id);
        if( sub.isEmpty() ){
            throw new SubNotFound("Sub not found with id : " + id);
        }
        sub.get().setSub(subRequest.getName());
        try{
            Sub subSaved = subRepository.save(sub.get());
            return MapperToDto.SubtoDto(subSaved);
        }catch (Exception e){
            e.printStackTrace();
            throw new ErrorException("Save Error : " + e.getMessage());
        }
    }

    @Override
    public Boolean deleteSub(String id) {
        Optional<Sub> sub = subRepository.findSubInSubFilm(id);
        if( sub.isEmpty() ){
            try{
                subRepository.deleteById(id);
            }catch (Exception e){
                e.printStackTrace();
                throw new ErrorException("Delete Error : " + e.getMessage());
            }
            return true;
        }
        return false;
    }
}
