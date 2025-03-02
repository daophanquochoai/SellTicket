package doctorhoai.learn.roomservice.service.inter;

import doctorhoai.learn.roomservice.dto.ChairDto;

import java.util.List;

public interface ChairService {
    ChairDto addChair(ChairDto chairDto);
    ChairDto updateChair(String id, ChairDto chairDto);
    ChairDto getChair(String id);
    List<ChairDto> getAllChair();
    void deleteChair(String id);
    void activeChair(String id);
    List<ChairDto> getChairByCustom(String limit, String page, String orderBy, String q, String asc, String status);
}
