package doctorhoai.learn.proxy_client.business.user.service.fallback;

import doctorhoai.learn.proxy_client.business.user.model.RoleDto;
import doctorhoai.learn.proxy_client.business.user.model.response.Response;
import doctorhoai.learn.proxy_client.business.user.service.RoleFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleFeignFallBack implements FallbackFactory<RoleFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public RoleFeign create(Throwable cause) {
        return new RoleFeign() {
            @Override
            public ResponseEntity<Response> getAllRoles() {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> addRole(RoleDto roleDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> deleteRole(int id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateRole(int id, RoleDto roleDto) {
                return functionCommon.process(cause);
            }
        };
    }
}
