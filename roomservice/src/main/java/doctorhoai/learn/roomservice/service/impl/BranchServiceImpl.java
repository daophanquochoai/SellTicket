package doctorhoai.learn.roomservice.service.impl;

import doctorhoai.learn.roomservice.dto.BranchDto;
import doctorhoai.learn.roomservice.entity.Branch;
import doctorhoai.learn.roomservice.entity.Room;
import doctorhoai.learn.roomservice.entity.Status;
import doctorhoai.learn.roomservice.exception.BranchCantRemove;
import doctorhoai.learn.roomservice.exception.BranchNotFound;
import doctorhoai.learn.roomservice.helper.MapperToDto;
import doctorhoai.learn.roomservice.repository.BranchRepository;
import doctorhoai.learn.roomservice.repository.RoomRepository;
import doctorhoai.learn.roomservice.service.inter.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final RoomRepository roomRepository;

    @Override
    public BranchDto addBranch(BranchDto branchDto) {
        Branch branch = Branch.builder()
                .nameBranch(branchDto.getNameBranch())
                .address(branchDto.getAddress())
                .status(Status.ACTIVE)
                .build();
        try{
            Branch branchSaved = branchRepository.save(branch);
            return MapperToDto.BranchToDto(branchSaved);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BranchNotFound(e.getMessage());
        }
    }

    @Override
    public BranchDto updateBranch(String id, BranchDto branchDto) {
        Optional<Branch> branch = branchRepository.findById(id);
        if( branch.isEmpty()){
            throw new BranchNotFound("Branch not found with id : " + id);
        }
        Branch branchOld = branch.get();
        if( branchOld.getStatus() != Status.valueOf(branchDto.getStatus())){
            if( branchOld.getStatus() == Status.ACTIVE){
                List<Room> rooms = roomRepository.getRoomsByBranch_IdAndStatus(id, Status.ACTIVE);
                if( rooms.size() > 0 ){
                    throw new BranchCantRemove("Branch still has room not deleted");
                }
            }
        }
        branchOld.setNameBranch(branchDto.getNameBranch());
        branchOld.setAddress(branchDto.getAddress());
        branchOld.setStatus(Status.valueOf(branchDto.getStatus()));
        try{
            Branch branchSaved = branchRepository.save(branchOld);
            return MapperToDto.BranchToDto(branchSaved);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new BranchNotFound(e.getMessage());
        }
    }

    @Override
    public BranchDto getBranch(String id) {
        Optional<Branch> branch = branchRepository.findById(id);
        if( branch.isEmpty()){
            throw new BranchNotFound("Branch not found with id : " + id);
        }
        return MapperToDto.BranchToDto(branch.get());
    }

    @Override
    public List<BranchDto> getAllBranch() {
        return branchRepository.getBranchByStatus(Status.ACTIVE).stream().map(MapperToDto::BranchToDto).toList();
    }

    @Override
    public void deleteBranch(String id) {
        Optional<Branch> branch = branchRepository.findById(id);
        if( branch.isEmpty()){
            throw new BranchNotFound("Branch not found with id : " + id);
        }
        Branch branchOld = branch.get();
        branchOld.setStatus(Status.DELETE);
        try{
            branchRepository.save(branchOld);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BranchNotFound(e.getMessage());
        }
    }

    @Override
    public void activeBranch(String id) {
        Optional<Branch> branch = branchRepository.findById(id);
        if( branch.isEmpty()){
            throw new BranchNotFound("Branch not found with id : " + id);
        }
        Branch branchOld = branch.get();
        branchOld.setStatus(Status.ACTIVE);
        try{
            branchRepository.save(branchOld);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BranchNotFound(e.getMessage());
        }
    }

    @Override
    public Page<BranchDto> getBranchByCustom(String limit, String page, String q, String orderBy, String asc, String status) {
        Page<Branch> branches;
        Pageable pageable;
        if( asc.equals("asc")){
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy));
        }else{
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by(orderBy).descending());
        }
        if( status.equals("none")){
            branches = branchRepository.getBranchByCustom(pageable,q);
        }else{
            branches = branchRepository.getBranchByCustom(pageable,q,Status.valueOf(status));
        }
        return branches.map(MapperToDto::BranchToDto);
    }
}
