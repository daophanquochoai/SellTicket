package doctorhoai.learn.roomservice.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.roomservice.dto.BranchDto;
import doctorhoai.learn.roomservice.dto.ChairDto;
import doctorhoai.learn.roomservice.dto.RoomDto;
import doctorhoai.learn.roomservice.entity.Branch;
import doctorhoai.learn.roomservice.entity.Chair;
import doctorhoai.learn.roomservice.entity.Room;
import doctorhoai.learn.roomservice.entity.Status;
import doctorhoai.learn.roomservice.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperToDto {

    public static Chair DtoToChair(ChairDto dto){
        return Chair.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Status.valueOf(dto.getStatus().toUpperCase()))
                .build();
    }

    public static ChairDto ChairToDto(Chair chair){
        return ChairDto.builder()
                .id(chair.getId())
                .name(chair.getName())
                .description(chair.getDescription())
                .status(chair.getStatus().toString())
                .build();
    }

    public static Branch DtoToBranch(BranchDto dto){
        if( dto == null ) return null;
        return Branch.builder()
                .id(dto.getId())
                .nameBranch(dto.getNameBranch())
                .address(dto.getAddress())
                .status(Status.valueOf(dto.getStatus().toUpperCase()))
                .build();
    }

    public static BranchDto BranchToDto(Branch branch){
        if( branch == null ) return null;
        return BranchDto.builder()
                .id(branch.getId())
                .nameBranch(branch.getNameBranch())
                .address(branch.getAddress())
                .status(branch.getStatus().toString())
                .build();
    }

    public static Room DtoToRoom(RoomDto dto){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return Room.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .positionChair(dto.getPositionChair())
                    .branch(MapperToDto.DtoToBranch(dto.getBranch()))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
    public static RoomDto RoomToDto( Room room){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return RoomDto.builder()
                    .id(room.getId())
                    .name(room.getName())
                    .positionChair(room.getPositionChair())
                    .branch(MapperToDto.BranchToDto(room.getBranch()))
                    .status(room.getStatus().toString())
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ErrorException(e.getMessage());
        }
    }
}
