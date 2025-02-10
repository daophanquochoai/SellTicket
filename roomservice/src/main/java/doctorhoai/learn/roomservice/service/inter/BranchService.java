package doctorhoai.learn.roomservice.service.inter;

import doctorhoai.learn.roomservice.dto.BranchDto;

import java.util.List;

public interface BranchService {
    BranchDto addBranch(BranchDto branchDto);
    BranchDto updateBranch(String id,BranchDto branchDto);
    BranchDto getBranch(String id);
    List<BranchDto> getAllBranch();
    void deleteBranch(String id);
    void activeBranch(String id);
}
