package doctorhoai.learn.proxy_client.business.payment.service.fallback;

import doctorhoai.learn.proxy_client.BaseDomain.Response;
import doctorhoai.learn.proxy_client.business.payment.model.TicketDto;
import doctorhoai.learn.proxy_client.business.payment.service.TicketFeign;
import doctorhoai.learn.proxy_client.security.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketFallBack implements FallbackFactory<TicketFeign> {

    private final FunctionCommon functionCommon;

    @Override
    public TicketFeign create(Throwable cause) {
        return new TicketFeign() {
            @Override
            public ResponseEntity<Response> addTicket(TicketDto ticketDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> updateTicket(String id, TicketDto ticketDto) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getTicket(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getAllTicket(String limit, String page, String active, String orderBy, String asc, String q) {
                return functionCommon.process(cause);
            }
            @Override
            public ResponseEntity<Response> deleteTicket(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> activateTicket(String id) {
                return functionCommon.process(cause);
            }

            @Override
            public ResponseEntity<Response> getTicketByActive() {
                return functionCommon.process(cause);
            }
        };
    }
}
