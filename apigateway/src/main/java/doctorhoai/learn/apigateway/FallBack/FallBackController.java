package doctorhoai.learn.apigateway.FallBack;

import doctorhoai.learn.apigateway.FallBack.response.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {
    @RequestMapping("/userSupport")
    public Mono<Response> userSupport() {
        return Mono.just(Response.builder()
                        .statusCode(500)
                        .message("User service not available")
                .build());
    }
    @RequestMapping("/rateSupport")
    public Mono<Response> rateSupport() {
        return Mono.just(Response.builder()
                .statusCode(500)
                .message("Rate service not available")
                .build());
    }
    @RequestMapping("/filmSupport")
    public Mono<Response> filmSupport() {
        return Mono.just(Response.builder()
                .statusCode(500)
                .message("Film service not available")
                .build());
    }
    @RequestMapping("/dishSupport")
    public Mono<Response> dishSupport() {
        return Mono.just(Response.builder()
                .statusCode(500)
                .message("Dish service not available")
                .build());
    }
}
