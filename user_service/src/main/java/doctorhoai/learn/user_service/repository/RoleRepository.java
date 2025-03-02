package doctorhoai.learn.user_service.repository;

import doctorhoai.learn.user_service.entity.Role;
import doctorhoai.learn.user_service.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r where r.status = :status and (r.roleName like concat('%', :q, '%'))")
    Page<Role> getAllRole(Pageable pageable, String q, Status status );
    @Query("select r from Role r where r.roleName like concat('%', :q, '%')")
    Page<Role> getAllRole(Pageable pageable, String q);

}
