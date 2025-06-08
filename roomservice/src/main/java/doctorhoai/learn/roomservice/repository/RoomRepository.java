package doctorhoai.learn.roomservice.repository;

import doctorhoai.learn.roomservice.entity.Room;
import doctorhoai.learn.roomservice.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Query("select r from Room r where r.status = :status and (r.name like concat('%', :q, '%'))")
    Page<Room> getRoomByCustom(Pageable pageable, String q, Status status);
    @Query("select r from Room r where (r.name like concat('%', :q, '%'))")
    Page<Room> getRoomByCustom(Pageable pageable, String q);
    List<Room> getRoomsByBranch_Id(String id);
    List<Room> getRoomsByBranch_IdAndStatus(String id, Status status);
    List<Room> getRoomsByStatus(Status status);
}
