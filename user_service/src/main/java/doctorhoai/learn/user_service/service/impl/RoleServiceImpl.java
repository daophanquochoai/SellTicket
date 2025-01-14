package doctorhoai.learn.user_service.service.impl;

import doctorhoai.learn.user_service.dto.RoleDto;
import doctorhoai.learn.user_service.entity.Role;
import doctorhoai.learn.user_service.entity.Status;
import doctorhoai.learn.user_service.exception.ErrorException;
import doctorhoai.learn.user_service.exception.RoleNotFound;
import doctorhoai.learn.user_service.helper.MapperToDto;
import doctorhoai.learn.user_service.repository.RoleRepository;
import doctorhoai.learn.user_service.service.inter.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void deleteRole(int id) {
        Optional<Role> roleSaved = roleRepository.findById(id);
        if( roleSaved.isEmpty()){
            throw new RoleNotFound("Role not found with id : " + id);
        }
        roleSaved.get().setStatus(Status.DELETE);
        try{
            roleRepository.save(roleSaved.get());
        }catch(Exception e){
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public RoleDto getRole(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if( role.isEmpty()){
            throw new RoleNotFound("Can't find role with id : id");
        }
        try{
            return MapperToDto.RoleToDto(role.get());
        }catch (Exception e){
            log.error("Can't get role with id : id");
            throw new ErrorException(e.getMessage());
        }
    }

    @Override
    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream().map( role -> {
            return RoleDto.builder()
                    .id(role.getId())
                    .roleName(role.getRoleName())
                    .status(role.getStatus().toString())
                    .build();
        }).toList();
    }

    @Override
    @Transactional
    public RoleDto addRole(RoleDto role) {
        try{
            Role r = new Role( role.getRoleName(), Status.valueOf(role.getStatus().toUpperCase()));
            Role roleSaved = roleRepository.save(r);
            return MapperToDto.RoleToDto(roleSaved);
        }catch (Exception ex ){
            log.error("Add role error : " + ex.getMessage());
            throw new ErrorException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public RoleDto updateRole(int id, RoleDto role) {
        Optional<Role> r = roleRepository.findById(id);
        if( r.isEmpty()){
            throw new RoleNotFound("Can't find role with id : id");
        }
        Role roleNew = new Role(id, role.getRoleName(), Status.valueOf(role.getStatus().toUpperCase()));
        try{
            Role savedRole = roleRepository.save(roleNew);
            return MapperToDto.RoleToDto(savedRole);
        }catch ( Exception ex ){
            log.error("Update role error : " + ex.getMessage());
            throw new ErrorException(ex.getMessage());
        }
    }
}
