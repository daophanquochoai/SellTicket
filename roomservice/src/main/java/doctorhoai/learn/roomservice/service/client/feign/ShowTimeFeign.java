package doctorhoai.learn.roomservice.service.client.feign;

import doctorhoai.learn.roomservice.dto.response.Response;
import doctorhoai.learn.roomservice.service.client.fallback.ShowTimeFallBack;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "showtimeservice", contextId = "showTimeClient", fallbackFactory = ShowTimeFallBack.class, path = "/filmshowtime")
public interface ShowTimeFeign {
    @GetMapping("/{roomId}/check/all")
    ResponseEntity<Response> getFilmShowByRoomAndActive(
            @PathVariable @NotNull String roomId
    );
}
