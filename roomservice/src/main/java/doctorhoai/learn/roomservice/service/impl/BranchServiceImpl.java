package doctorhoai.learn.roomservice.service.impl;

import doctorhoai.learn.roomservice.dto.BranchDto;
import doctorhoai.learn.roomservice.entity.Branch;
import doctorhoai.learn.roomservice.entity.Status;
import doctorhoai.learn.roomservice.exception.BranchNotFound;
import doctorhoai.learn.roomservice.helper.MapperToDto;
import doctorhoai.learn.roomservice.repository.BranchRepository;
import doctorhoai.learn.roomservice.service.inter.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
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
        return branchRepository.findAll().stream().map(MapperToDto::BranchToDto).toList();
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
}
