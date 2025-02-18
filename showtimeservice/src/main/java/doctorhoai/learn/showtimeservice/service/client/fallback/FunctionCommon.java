package doctorhoai.learn.showtimeservice.service.client.fallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.showtimeservice.dto.response.ErrorResponse;
import doctorhoai.learn.showtimeservice.dto.response.Response;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FunctionCommon {
    public ResponseEntity<Response> process(Throwable cause){
        if( cause instanceof ExecutionException){
            if( cause.getCause() instanceof FeignException){
                FeignException fe = (FeignException) cause.getCause();
                int statusCode = fe.status();
                try {
                    // Parse nội dung body từ FeignException
                    String errorContent = fe.contentUTF8();
                    // Tạo ObjectMapper
                    ObjectMapper objectMapper = new ObjectMapper();

                    // Deserialize JSON thành ErrorResponse
                    ErrorResponse errorResponse = objectMapper.readValue(errorContent, ErrorResponse.class);

                    // Xây dựng Response từ ErrorResponse
                    return ResponseEntity.status(errorResponse.getStatusCode()).body(
                            Response.builder()
                                    .statusCode(errorResponse.getStatusCode())
                                    .message(errorResponse.getMessage())
                                    .build()
                    );
                } catch (Exception e) {
                    // Trường hợp lỗi khi parse body
                    return ResponseEntity.status(statusCode).body(
                            Response.builder()
                                    .statusCode(statusCode)
                                    .message(fe.contentUTF8())
                                    .build()
                    );
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Response.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message("** Service downed !")
                        .build()
        );
    }
}
