package doctorhoai.learn.roomservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.roomservice.dto.RoomDto;
import doctorhoai.learn.roomservice.dto.resquest.RoomRequest;
import doctorhoai.learn.roomservice.entity.Branch;
import doctorhoai.learn.roomservice.entity.Room;
import doctorhoai.learn.roomservice.entity.Status;
import doctorhoai.learn.roomservice.exception.BranchNotFound;
import doctorhoai.learn.roomservice.exception.ErrorException;
import doctorhoai.learn.roomservice.exception.RoomNotFound;
import doctorhoai.learn.roomservice.helper.MapperToDto;
import doctorhoai.learn.roomservice.repository.BranchRepository;
import doctorhoai.learn.roomservice.repository.RoomRepository;
import doctorhoai.learn.roomservice.service.inter.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;
    private final BranchRepository branchRepository;

    @Override
    public RoomDto addRoom(RoomRequest roomRequest) {
        Optional<Branch> branchOptional = branchRepository.findById(roomRequest.getBranchId());
        if( branchOptional.isEmpty() ){
            throw new BranchNotFound("Branch not found with id : " + roomRequest.getBranchId());
        }
        Branch branch = branchOptional.get();
        try{
            Room room = Room.builder()
                    .name(roomRequest.getName())
                    .positionChair(roomRequest.getPositionChair())
                    .branch(branch)
                    .status(Status.valueOf(roomRequest.getStatus().toUpperCase()))
                    .build();
            Room roomSaved = roomRepository.save(room);
            return MapperToDto.RoomToDto(roomSaved);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public RoomDto updateRoom(String id, RoomRequest roomRequest) {
        try{
            Optional<Room> roomOptional = roomRepository.findById(id);
            if( roomOptional.isEmpty() ){
                throw new RoomNotFound("Room not found with id : " + id);
            }
            Room room = roomOptional.get();
            room.setName(roomRequest.getName());
            room.setPositionChair( roomRequest.getPositionChair());
            if( !roomOptional.get().getBranch().getId().equals(roomRequest.getBranchId()) ){
                Optional<Branch> branchOptional1 = branchRepository.findById(roomRequest.getBranchId());
                if( branchOptional1.isEmpty() ){
                    throw new BranchNotFound("Branch not found with id : " + roomRequest.getBranchId());
                }
                room.setBranch(branchOptional1.get());
            }
            room.setStatus(Status.valueOf(roomRequest.getStatus().toUpperCase()));
            Room roomSaved = roomRepository.save(room);
            return MapperToDto.RoomToDto(roomSaved);
        }
        catch (BranchNotFound branchNotFound){
            log.error(branchNotFound.getMessage());
            throw new BranchNotFound(branchNotFound.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public RoomDto getRoom(String id) {
        try{
            Optional<Room> roomOptional = roomRepository.findById(id);
            if( roomOptional.isEmpty() ){
                throw new RoomNotFound("Room not found with id : " + id);
            }
            Room room = roomOptional.get();
            return MapperToDto.RoomToDto(room);
        }
        catch (RoomNotFound roomNotFound){
            log.error(roomNotFound.getMessage());
            throw new RoomNotFound(roomNotFound.getMessage());
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(MapperToDto::RoomToDto).toList();
    }

    @Override
    public void deleteRoom(String id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if( roomOptional.isEmpty() ){
            throw new RoomNotFound("Room not found with id : " + id);
        }
        Room room = roomOptional.get();
        room.setStatus(Status.DELETE);
        try{
            roomRepository.save(room);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public void activeRoom(String id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if( roomOptional.isEmpty() ){
            throw new RoomNotFound("Room not found with id : " + id);
        }
        Room room = roomOptional.get();
        room.setStatus(Status.ACTIVE);
        try{
            roomRepository.save(room);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
}
