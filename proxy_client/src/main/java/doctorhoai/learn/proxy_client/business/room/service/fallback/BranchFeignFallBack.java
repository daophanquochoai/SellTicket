package doctorhoai.learn.proxy_client.business.room.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.room.model.BranchDto;
import doctorhoai.learn.proxy_client.business.room.service.BranchFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BranchFeignFallBack implements FallbackFactory<BranchFeign> {

    private final FunctionCommon functionCommon;
    @Override
    public BranchFeign create(Throwable cause) {
        return new BranchFeign() {
            @Override
            public ResponseEntity<Response> addBranch(BranchDto branchDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateBranch(String id, BranchDto branchDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getBranchById(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllBranch() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteBranch(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activateBranch(String id) {
                return functionCommon.process(cause);
            }
        };
    }
}
