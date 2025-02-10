package doctorhoai.learn.roomservice.service.impl;

import doctorhoai.learn.roomservice.dto.ChairDto;
import doctorhoai.learn.roomservice.entity.Chair;
import doctorhoai.learn.roomservice.entity.Status;
import doctorhoai.learn.roomservice.exception.ChairNotFound;
import doctorhoai.learn.roomservice.exception.ErrorException;
import doctorhoai.learn.roomservice.helper.MapperToDto;
import doctorhoai.learn.roomservice.repository.ChairRepository;
import doctorhoai.learn.roomservice.service.inter.ChairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChairServiceImpl implements ChairService {

    private final ChairRepository chairRepository;

    @Override
    public ChairDto addChair(ChairDto chairDto) {
        Chair chair = Chair.builder()
                .name(chairDto.getName())
                .description(chairDto.getDescription())
                .status(Status.ACTIVE)
                .build();
        try{
            Chair chairSaved = chairRepository.save(chair);
            return MapperToDto.ChairToDto(chairSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public ChairDto updateChair(String id, ChairDto chairDto) {
        Optional<Chair> chairOptional =chairRepository.findById(id);
        if( chairOptional.isEmpty() ){
            throw new ChairNotFound("Chair not found with id : " + id);
        }
        Chair chairNew = chairOptional.get();
        chairNew.setName(chairDto.getName());
        chairNew.setDescription(chairDto.getDescription());
        chairNew.setStatus(Status.valueOf(chairDto.getStatus().toUpperCase()));
        try{
            Chair chairSaved = chairRepository.save(chairNew);
            return MapperToDto.ChairToDto(chairSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public ChairDto getChair(String id) {
        Optional<Chair> chairOptional =chairRepository.findById(id);
        if( chairOptional.isEmpty() ){
            throw new ChairNotFound("Chair not found with id : " + id);
        }
        return MapperToDto.ChairToDto(chairOptional.get());
    }

    @Override
    public List<ChairDto> getAllChair() {
        return chairRepository.findAll().stream().map(MapperToDto::ChairToDto).toList();
    }

    @Override
    public void deleteChair(String id) {
        Optional<Chair> chairOptional = chairRepository.findById(id);
        if( chairOptional.isEmpty() ){
            throw new ChairNotFound("Chair not found with id : " + id);
        }
        Chair chairDeleted = chairOptional.get();
        chairDeleted.setStatus(Status.DELETE);
        try{
            chairRepository.save(chairDeleted);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public void activeChair(String id) {
        Optional<Chair> chairOptional = chairRepository.findById(id);
        if( chairOptional.isEmpty() ){
            throw new ChairNotFound("Chair not found with id : " + id);
        }
        Chair chairDeleted = chairOptional.get();
        chairDeleted.setStatus(Status.ACTIVE);
        try{
            chairRepository.save(chairDeleted);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
}
